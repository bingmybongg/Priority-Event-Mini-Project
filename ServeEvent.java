import java.util.Optional;

class ServeEvent extends Event {
    private final Server server;
    private final boolean leftQueue;

    ServeEvent(Customer customer, double eventTime, Server server) {
        super(customer, eventTime);
        this.server = server;
        this.leftQueue = false;
    }
    
    ServeEvent(Customer customer, double eventTime, Server server, boolean leftQueue) {
        super(customer, eventTime);
        this.server = server;
        this.leftQueue = leftQueue;
    }
    
    public boolean isServe() {
        return true;
    }
    
    public boolean isLeave() {
        return false;
    }
    
    public double waitTime() {
        return 0;
    }
    
    public boolean isRepeated() {
        return false;
    }

    public Optional<Pair<Optional<Event>, Shop>> next(Shop shop) {
        Server updatedServer = shop.serve(this.server, super.eventTime);
        Customer updatedCustomer = super.customer;
        if (this.leftQueue) {
            updatedServer = updatedServer.exitQueue();
            updatedCustomer = updatedCustomer.newArrival(super.eventTime);
        }
        double newEventTime = updatedServer.doneService();
        updatedServer = updatedServer.doneServing();
        Shop updatedShop = shop.update(updatedServer);
        DoneEvent doneEvent = new DoneEvent(updatedCustomer, newEventTime);
        return Optional.of(new Pair<Optional<Event>, Shop>(Optional.of(doneEvent), updatedShop));
    }

    public String toString() {
        return super.toString() + " serves by " + this.server.toString();
    }
}
