package by.kamtech.telegrambot.counterman.dao;

import javax.persistence.*;

@Entity
@Table (name = "tbl_expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_expense_id")
    @SequenceGenerator(name = "seq_expense_id", sequenceName = "seq_expense_id")
    private int id;

    @Column (name = "user_id")
    private int userId;

    @Column (name = "chat_id")
    private long chatId;

    @Column
    private double amount;

    public Expense() {    }

    public Expense (int userId, long chatId, double amount) {
        this.userId = userId;
        this.chatId = chatId;
        this.amount = amount;
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

    public double getAmount() {
        return this.amount;
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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Expense)) return false;
        final Expense other = (Expense) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        if (this.getUserId() != other.getUserId()) return false;
        if (this.getChatId() != other.getChatId()) return false;
        if (Double.compare(this.getAmount(), other.getAmount()) != 0) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Expense;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        result = result * PRIME + this.getUserId();
        final long $chatId = this.getChatId();
        result = result * PRIME + (int) ($chatId >>> 32 ^ $chatId);
        final long $amount = Double.doubleToLongBits(this.getAmount());
        result = result * PRIME + (int) ($amount >>> 32 ^ $amount);
        return result;
    }

    public String toString() {
        return "Expense(id=" + this.getId() + ", userId=" + this.getUserId() + ", chatId=" + this.getChatId() + ", amount=" + this.getAmount() + ")";
    }
}
