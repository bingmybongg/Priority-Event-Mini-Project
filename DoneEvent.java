import java.util.Optional;

class DoneEvent extends Event {
    public DoneEvent(Customer customer, double eventTime) {
        super(customer, eventTime);
    }
    
    public double waitTime() {
        return super.customer.waitTime();
    }
    
    public boolean isServe() {
        return false;
    }
    
    public boolean isLeave() {
        return false;
    }
    
    public boolean isRepeated() {
        return false;
    }

    public Optional<Pair<Optional<Event>, Shop>> next(Shop shop) {
        return Optional.of(new Pair<Optional<Event>, Shop>(Optional.empty(), shop));
    }

    @Override
    public String toString() {
        return super.toString() + " done";
    }
}
