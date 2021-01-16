public class NodeInfo {

    public int nodeID;
    public String multicast_address;
    public int xCoord;
    public int yCoord;

    public NodeInfo ( int nodeID, String multicast_address, int xCoord, int yCoord ) {
        this.nodeID = nodeID;
        this.multicast_address = multicast_address;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID( int nodeID ) {
        this.nodeID = nodeID;
    }

    public String getMulticastAddress() {
        return multicast_address;
    }

    public void setMulticastAddress( String multicast_address ) {
        this.multicast_address = multicast_address;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord( int xCoord ) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord( int yCoord ) {
        this.yCoord = yCoord;
    }
}
