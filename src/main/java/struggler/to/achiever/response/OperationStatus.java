package struggler.to.achiever.response;

public enum OperationStatus {

    DELETE("Delete Operation Performed"),
    DELETED("User Detail deleted");


    OperationStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
