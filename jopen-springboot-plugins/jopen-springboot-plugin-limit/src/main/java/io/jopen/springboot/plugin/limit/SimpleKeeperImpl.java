package io.jopen.springboot.plugin.limit;

import com.google.common.base.Verify;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * IP/Token拉黑策略设定
 * <p>
 * <p>
 * {@link Keeper} 的默认实现策略
 *
 * @author maxuefeng
 * @since 2020/2/5
 */
@Component
public final class SimpleKeeperImpl implements Keeper {


    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public SimpleKeeperImpl(@NonNull RedisTemplate<String, Object> redisTemplate) {
        Verify.verify(this.freezingTime() > 0L, "freezingTime must be gt zero");
        Verify.verify(this.exceedViolation() > 0, "exceedViolation must be gt zero");
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void recordViolation(@NonNull String limitKey) {
        String redisKey = redisKey(limitKey);
        ViolationRecord violationRecord = (ViolationRecord) redisTemplate.opsForValue().get(redisKey);
        violationRecord = Optional.ofNullable(violationRecord)
                .map(vr -> {
                    vr.setEndViolationTime(new Date().getTime());
                    vr.setViolationCount((vr.getViolationCount() + 1));
                    return vr;
                })
                .orElseGet(() -> {
                    long time = new Date().getTime();
                    return new ViolationRecord(time, time, 1);
                });

        // setup the value
        redisTemplate.opsForValue().set(redisKey, violationRecord);

        if (violationRecord.getViolationCount() == 1) {
            // setup expire time
            redisTemplate.expire(redisKey, this.freezingTime(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public @NonNull Info solicitingOpinions(@NonNull String limitKey) {
        String redisKey = redisKey(limitKey);
        boolean exist = redisTemplate.hasKey(redisKey) != null;
        if (exist) {
            ViolationRecord violationRecord = (ViolationRecord) redisTemplate.opsForValue().get(redisKey);
            return Optional.ofNullable(violationRecord)
                    .map(vr -> vr.getViolationCount() >= this.exceedViolation())
                    .orElse(false) ? Info.FORBIDDEN : Info.FORBIDDEN;
        } else {
            return Info.NORMAL;
        }
    }
}
