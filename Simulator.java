import java.util.List;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.function.Supplier;
import java.util.Optional;

class Simulator {
    private final int numOfServers;
    private final int numOfCustomers;
    private final List<Pair<Integer, Double>> arrivals;
    private final int qmax;
    private final Supplier<Double> serviceTime;

    Simulator(int numOfServers, int qmax, Supplier<Double> serviceTime, int numOfCustomers, 
              List<Pair<Integer, Double>> arrivals) {
        this.numOfServers = numOfServers;
        this.numOfCustomers = numOfCustomers;
        this.arrivals = arrivals;
        this.serviceTime = serviceTime;
        this.qmax = qmax;
    }

    Pair<String, String> run() {
        List<ArriveEvent> events = arrivals.stream()
                                           .map(x -> new ArriveEvent(
                                                     new Customer(
                                                     x.t(),
                                                     x.u()), 
                                                     x.u()))
                                           .toList();
        PQ<Event> pq = new PQ<Event>(events);
        State init = new State(pq, new Shop(this.numOfServers, this.serviceTime, this.qmax));
        
        List<Optional<State>> finalStates = Stream.iterate(Optional.of(init),
                                                ostate -> ostate.isPresent(),
                                                ostate -> ostate.flatMap(st -> st.next()))
                                                  .toList();
        String output = finalStates.stream()
                                   .map(ostate -> ostate.map(st -> st.toString()).orElse(""))
                                   .filter(str -> !str.isEmpty())
                                   .reduce("", (x, y) -> x + y + "\n"); 
        String stats = finalStates.get(finalStates.size() - 1)
                                  .map(st -> st.stats())
                                  .orElse("");  
                              
        return new Pair<String, String>(output, stats);
    }
}


