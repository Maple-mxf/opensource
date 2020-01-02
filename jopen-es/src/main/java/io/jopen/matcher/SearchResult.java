package io.jopen.matcher;

import java.io.Serializable;
import java.util.Map;

/**
 * @author maxuefeng
 * @since 2019/11/28
 */
public class SearchResult implements Serializable, Comparable<SearchResult> {

    private Map<String, Object> sourceAsMap;
    private Float score;

    public SearchResult(Map<String, Object> sourceAsMap, Float score) {
        this.sourceAsMap = sourceAsMap;
        this.score = score;
    }

    public Map<String, Object> getSourceAsMap() {
        return sourceAsMap;
    }

    public void setSourceAsMap(Map<String, Object> sourceAsMap) {
        this.sourceAsMap = sourceAsMap;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public int compareTo(SearchResult o) {
        if (this.score > o.getScore()) {
            return 1;
        } else if (score.equals(o.getScore())) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "sourceAsMap=" + sourceAsMap +
                ", score=" + score +
                '}';
    }
}
