import java.util.Optional;

class State {
    private final Shop shop;
    private final PQ<Event> pq;
    private final String output;
    private final int noOfServe;
    private final int noOfLeave;
    private final double waitTime;

    State(PQ<Event> pq, Shop shop) {
        this.pq = pq;
        this.shop = shop;
        this.output = "";
        this.noOfServe = 0;
        this.noOfLeave = 0;
        this.waitTime = 0.0;
    }

    private State(PQ<Event> pq, 
                  Shop shop, 
                  String output,
                  int noOfServe,
                  int noOfLeave,
                  double waitTime) {
        this.pq = pq;
        this.shop = shop;
        this.output = output;
        this.noOfServe = noOfServe;
        this.noOfLeave = noOfLeave;
        this.waitTime = waitTime;
    }
    
    public String stats() {
        double avgWaitTime;
        if (this.noOfServe == 0) {
            avgWaitTime = this.waitTime;
        } else {
            avgWaitTime = this.waitTime / this.noOfServe;
        }
        
        return String.format("[%.3f ", avgWaitTime) + 
               this.noOfServe + " " +
               this.noOfLeave + "]";
    }
        

    public Optional<State> next() {
        return this.pq.poll().t().flatMap(event -> {
            String output;
            if (event.isRepeated()) {
                output = "";
            } else {
                output = event.toString();
            }
            return event.next(this.shop)
                        .flatMap(e -> {
                            Optional<Event> currEvent = e.t();
                            Shop updatedShop = e.u();
                            return currEvent.map(currentE -> {
                                int serveEvent = 0;
                                int leaveEvent = 0;
                                double waitTime = currentE.waitTime();
                                PQ<Event> newPQ = this.pq
                                                      .poll()
                                                      .u()
                                                      .add(currentE); 
                                if (currentE.isServe()) {
                                    serveEvent += 1;
                                }
                                
                                if (currentE.isLeave()) {
                                    leaveEvent += 1;
                                }
                   
                                return new State(newPQ, 
                                                 updatedShop, 
                                                 output,
                                                 this.noOfServe + serveEvent,
                                                 this.noOfLeave + leaveEvent,
                                                 this.waitTime + waitTime); 
                            }).or(() -> Optional.of(new State(this.pq
                                                      .poll()
                                                      .u(), 
                                                      updatedShop, 
                                                      output,
                                                      this.noOfServe,
                                                      this.noOfLeave,
                                                      this.waitTime)));
                        });
        });    
    }

    @Override
    public String toString() {
        return this.output;
    }
}
