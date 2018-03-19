package infosolution.dev.com.queuemanagement.model;

import android.widget.TextView;

/**
 * Created by Shreyansh on 3/16/2018.
 */

public class QueueModel {
    public String getTokenQueue() {
        return TokenQueue;
    }

    public void setTokenQueue(String tokenQueue) {
        TokenQueue = tokenQueue;
    }

    String TokenQueue;

    public String getQueueId() {
        return QueueId;
    }

    public void setQueueId(String queueId) {
        QueueId = queueId;
    }

    String QueueId;

    public String getTransferBy() {
        return TransferBy;
    }

    public void setTransferBy(String transferBy) {
        TransferBy = transferBy;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    String TransferBy,Date;

    public String getTransferedAt() {
        return TransferedAt;
    }

    public void setTransferedAt(String transferedAt) {
        TransferedAt = transferedAt;
    }

    String TransferedAt;

}
