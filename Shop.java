import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.function.Supplier;

class Shop {
    private final int noOfServers;
    private final List<Server> listOfServers;
    private final Supplier<Double> serveTime;
    private final int maxQueue;

    Shop(int noOfServers, Supplier<Double> serveTime, int maxQueue) {
        this.noOfServers = noOfServers;
        this.listOfServers = initList(noOfServers);
        this.serveTime = serveTime;
        this.maxQueue = maxQueue;
    }

    private Shop(int noOfServers, 
                 Supplier<Double> serveTime, 
                 int maxQueue,
                 List<Server> listOfServers) {
        this.noOfServers = noOfServers;
        this.listOfServers = listOfServers;
        this.serveTime = serveTime;
        this.maxQueue = maxQueue;
    }

    public String toString() {
        return listOfServers.toString();
    }
    
    public Server serve(Server currServer, double eventTime) {
        double serviceTime = this.serveTime.get();
        return this.listOfServers.stream()
                                 .filter(s -> s.equals(currServer))
                                 .toList()
                                 .get(0)
                                 .serve(serviceTime + eventTime);
    }

    private List<Server> initList(int serverIds) {
        if (this.noOfServers > 0) {
            return IntStream.rangeClosed(1, serverIds)
                            .mapToObj(id -> new Server(id))
                            .toList();
        }
        return List.of();
    }
    
    public Optional<Server> findQueue() {
        return this.listOfServers.stream()
                                 .filter(server -> server.emptyQueue(this.maxQueue))
                                 .findFirst();
    }

    public Optional<Server> findServer(Customer customer) {
        return this.listOfServers.stream()
                                 .filter(server -> server.canServe(customer))
                                 .findFirst();
    }

    public Shop update(Server s) {
        List<Server> newList = this.listOfServers.stream()
                                                 .map(server -> server.equals(s) ? s : server)
                                                 .toList();
        return new Shop(this.noOfServers, this.serveTime, this.maxQueue, newList);
    }
    
    public double doneService(Server server) {
        return this.listOfServers.stream()
                                 .filter(s -> s.equals(server)) 
                                 .toList()
                                 .get(0)
                                 .doneService();
    }
    
    public boolean isDone(Server server) {
        return this.listOfServers.stream()
                                 .filter(s -> s.equals(server)) 
                                 .toList()
                                 .get(0)
                                 .isDone();
    }
    
    public boolean canServe(Server server, Customer customer) {
        return this.listOfServers.stream()
                                 .filter(s -> s.equals(server))
                                 .toList()
                                 .get(0)
                                 .canServe(customer);
    }
    
    public Server currServing(Server server) {
        return this.listOfServers.stream()
                                 .filter(s -> s.equals(server))
                                 .toList()
                                 .get(0)
                                 .currServing();
    }
}
