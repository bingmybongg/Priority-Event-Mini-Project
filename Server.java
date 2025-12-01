import java.util.Optional;

class Server {
    private final Integer id;
    private final double serveTime;
    private final Integer queue;
    private final boolean isDone;

    Server(int id) {
        this.id = id;
        this.serveTime = 0.0;
        this.queue = 0;
        this.isDone = true;
    }

    private Server(int id, double serveTime, int queue, boolean isDone) {
        this.id = id;
        this.serveTime = serveTime;
        this.queue = queue;
        this.isDone = isDone;
    }

    public boolean equals(Server s) {
        return this.id.equals(s.id);
    }
    
    public Customer newArrival(Customer customer) {
        return customer.newArrival(this.serveTime);
    }
    
    public boolean emptyQueue(int maxQueue) {
        return this.queue < maxQueue;
    }
    
    public double doneService() {
        return this.serveTime;
    }

    public String toString() {
        return "server " + this.id;
    }
    
    public Server joinQueue() {
        return new Server(this.id, this.serveTime, this.queue + 1, this.isDone);
    }
    
    public Server exitQueue() {
        return new Server(this.id, this.serveTime, this.queue - 1, this.isDone);
    }
    
    public Server serve(double serviceTime) {
        return new Server(this.id, serviceTime, this.queue, this.isDone);
    }

    public boolean canServe(Customer customer) {
        return customer.canBeServed(this.serveTime); 
    }
    
    public Server currServing() {
        return new Server(this.id, this.serveTime, this.queue, false);
    }
    
    public Server doneServing() {
        return new Server(this.id, this.serveTime, this.queue, true);
    }
    
    public boolean isDone() {
        return this.isDone;
    }
}
