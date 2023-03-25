package com.example.lab_map_javafx.domainDTO;

import java.time.LocalDateTime;

public class MessageDTO {
    private Long from;
    private Long to;
    private LocalDateTime date;
    private String inbox;
    private String sent;
    private String received;

    public MessageDTO(Long from, Long to, LocalDateTime date, String inbox, String sent, String received) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.inbox = inbox;
        this.sent = sent;
        this.received = received;
    }

    public Long getFrom() {
        return from;
    }

    public Long getTo() {
        return to;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getInbox() {
        return inbox;
    }

    public String getSent() {
        return sent;
    }

    public String getReceived() {
        return received;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "from=" + from +
                ", to=" + to +
                ", date=" + date +
                ", inbox='" + inbox + '\'' +
                ", sent='" + sent + '\'' +
                ", received='" + received + '\'' +
                '}';
    }
}
