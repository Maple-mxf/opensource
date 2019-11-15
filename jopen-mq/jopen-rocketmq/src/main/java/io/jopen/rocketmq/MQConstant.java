package io.jopen.rocketmq;


/**
 * MQ常量
 */
public interface MQConstant {

    /**/
    @Deprecated
    String CONSUMER_GROUP = "ocr_consumer_group";

    /**/
    @Deprecated
    String PRODUCE_GROUP = "ocr_produce_group";

    /*OCR识别完成发送到的Topic*/
    String OCR_Result_Topic = "ocrResultTopic";

    /*接受OCR识别请求*/
    String OCR_Request_Topic = "ocrRequestTopic";

    /**/
    String OCR_RESULT_PRODUCE_GROUP = "ocrResultProduceGroup";

    /**/
    String OCR_RESULT_CONSUME_GROUP = "ocrResultConsumeGroup";

    /**/
    String OCR_REQUEST_PRODUCE_GROUP = "ocrRequestProduceGroup";

    /**/
    String OCR_REQUEST_CONSUME_GROUP = "ocrRequestConsumeGroup";
}
