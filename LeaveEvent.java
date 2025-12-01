import java.util.Optional;

class LeaveEvent extends Event {
    LeaveEvent(Customer customer, double eventTime) {
        super(customer, eventTime);
    }
    
    public double waitTime() {
        return 0;
    }
    
    public boolean isServe() {
        return false;
    }
    
    public boolean isLeave() {
        return true;
    }
    
    public boolean isRepeated() {
        return false;
    }

    @Override
    public Optional<Pair<Optional<Event>, Shop>> next(Shop shop) {
        return Optional.of(new Pair<Optional<Event>, Shop>(Optional.empty(), shop));
    }

    @Override
    public String toString() {
        return super.toString() + " leaves";
    }
}
