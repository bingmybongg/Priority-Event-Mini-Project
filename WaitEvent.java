import java.util.Optional;

class WaitEvent extends Event {
    private final Server server;
    private final boolean isRepeated;
    
    WaitEvent(Customer customer, double eventTime, Server server, boolean isRepeated) {
        super(customer, eventTime);
        this.server = server;
        this.isRepeated = isRepeated;
    }
    
    public boolean isServe() {
        return false;
    }
    
    public boolean isLeave() {
        return false;
    }
    
    public double waitTime() {
        return 0;
    }
    
    public boolean isRepeated() {
        return this.isRepeated;
    }
    
    public Optional<Pair<Optional<Event>, Shop>> next(Shop shop) {
        double newEventTime = shop.doneService(this.server);
        if (shop.isDone(this.server)) {
            Server updatedServer = shop.currServing(this.server);
            Shop updatedShop = shop.update(updatedServer);
            Event serveEvent = new ServeEvent(super.customer, newEventTime, updatedServer, true);
            return Optional.of(new Pair<Optional<Event>, Shop>(Optional.of(serveEvent), 
                                                               updatedShop));
        }
        Event waitEvent = new WaitEvent(super.customer, newEventTime, this.server, true);
        return Optional.of(new Pair<Optional<Event>, Shop>(Optional.of(waitEvent),
                                                           shop));
    }
    
    public String toString() {
        return super.toString() + " waits at " + this.server.toString();
    }
}