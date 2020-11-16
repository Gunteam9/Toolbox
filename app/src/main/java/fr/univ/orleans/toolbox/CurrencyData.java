package fr.univ.orleans.toolbox;

import java.time.LocalDateTime;

public class CurrencyData {

    private LocalDateTime date;
    private String source;
    private String target;
    private float value;
    private float quantity;
    private float amount;

    public CurrencyData(LocalDateTime date, String source, String target, float value, float quantity, float amount) {
        this.date = date;
        this.source = source;
        this.target = target;
        this.value = value;
        this.quantity = quantity;
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CurrencyData{" +
                "date=" + date +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", value=" + value +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}
