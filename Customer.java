class Customer {
    private final int id;
    private final double arrivalTime;
    private final double waitTime;

    Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.waitTime = 0.0;
    }
    
    Customer(int id, double arrivalTime, double waitTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.waitTime = waitTime;
    }
    
    public double waitTime() {
        if (this.waitTime == 0) {
            return 0;
        }
        
        return this.waitTime - this.arrivalTime;
    }
    
    public Customer newArrival(double time) {
        return new Customer(this.id, this.arrivalTime, time);
    }

    public double compareTime(Customer c) {
        return this.arrivalTime - c.arrivalTime;
    }

    public String toString() {
        return "customer " + this.id;
    }

    public boolean canBeServed(double time) {
        return (this.arrivalTime >= time) || (this.waitTime >= time);
    }
}

