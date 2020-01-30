package by.kamtech.telegrambot.counterman.dao;

import javax.persistence.*;

@Entity
@Table(name = "tbl_position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pos_id")
    @SequenceGenerator(name = "seq_pos_id", sequenceName = "seq_pos_id")
    private int id;

    @Column (name = "user_id")
    private int userId;

    @Column (name = "chat_id")
    private long chatId;

    @Column
    private int position;

    public Position() {
    }

    public Position(int userId, long chatId, int position) {
        this.userId = userId;
        this.chatId = chatId;
        this.position = position;
    }

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return this.userId;
    }

    public long getChatId() {
        return this.chatId;
    }

    public int getPosition() {
        return this.position;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Position)) return false;
        final Position other = (Position) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        if (this.getUserId() != other.getUserId()) return false;
        if (this.getChatId() != other.getChatId()) return false;
        if (this.getPosition() != other.getPosition()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Position;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        result = result * PRIME + this.getUserId();
        final long $chatId = this.getChatId();
        result = result * PRIME + (int) ($chatId >>> 32 ^ $chatId);
        result = result * PRIME + this.getPosition();
        return result;
    }

    public String toString() {
        return "Position(id=" + this.getId() + ", userId=" + this.getUserId() + ", chatId=" + this.getChatId() + ", position=" + this.getPosition() + ")";
    }
}
