import java.util.Optional;

class ArriveEvent extends Event {
    ArriveEvent(Customer customer, double eventTime) {
        super(customer, eventTime);
    }
    
    public double waitTime() {
        return 0;
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

    @Override
    public Optional<Pair<Optional<Event>, Shop>> next(Shop shop) {
        return shop.findServer(super.customer)
                   .map(availServer -> {
                       Server updatedServer = availServer.currServing();
                       Shop updatedShop = shop.update(updatedServer);
                       Event serveEvent = new ServeEvent(super.customer, 
                                                         super.eventTime,
                                                         updatedServer);

                       return new Pair<Optional<Event>, Shop>(Optional.of(serveEvent),
                                                              updatedShop);
                   })
                   .or(() -> shop.findQueue()
                                 .map(server -> {
                                     Server updatedServer = server.joinQueue();
                                     Customer updatedCustomer = updatedServer.newArrival(
                                                                              super.customer);
                                     Shop updatedShop = shop.update(updatedServer);
                                     Event waitEvent = new WaitEvent(updatedCustomer,
                                                                     super.eventTime,
                                                                     updatedServer,
                                                                     false);
                                     return new Pair<Optional<Event>, Shop>(Optional.of(waitEvent),
                                                                            updatedShop);
                                 })
                                 .or(() -> Optional.of(new Pair<Optional<Event>, Shop>(
                                           Optional.of(new LeaveEvent(super.customer, 
                                                                      super.eventTime)),
                                                                      shop))));
    }

    public String toString() {
        return super.toString() + " arrives";
    }
}
