import java.util.Optional;

abstract class Event implements Comparable<Event> {
    protected final double eventTime;
    protected final Customer customer;

    Event(Customer customer, double eventTime) {
        this.eventTime = eventTime;
        this.customer = customer;
    }

    public int compareTo(Event event) {
        if (this.eventTime > event.eventTime) {
            return 1;
        }

        if (this.eventTime < event.eventTime) {
            return -1;
        }
        
        if (this.customer
                .compareTime(event.customer) < 0) {
            return -1;
        }

        if (this.customer
                .compareTime(event.customer) > 0) {
            return 1;
        }

        return 0;
    }

    public abstract boolean isServe();
    
    public abstract boolean isLeave();
    
    public abstract double waitTime();
    
    public abstract Optional<Pair<Optional<Event>, Shop>> next(Shop shop);
    
    public abstract boolean isRepeated();

    public String toString() {
        return String.format("%.3f ", this.eventTime) + this.customer.toString();
    }
}
