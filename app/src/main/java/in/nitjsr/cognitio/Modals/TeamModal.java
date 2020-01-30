package in.nitjsr.cognitio.Modals;

public class TeamModal {
    private String memberName, memberDesignation;
    private int memberImage;
    private String contactNo;
    private String whatsappNo;
    private String description;
    private String image;
    private String dept;

    public TeamModal(String memberName, String menberDesignation, int memberImage, String whatsappNo, String dept) {
        this.memberDesignation = menberDesignation;
        this.memberImage = memberImage;
        this.memberName = memberName;
        this.whatsappNo = whatsappNo;
        this.dept = dept;
    }

    public TeamModal(String image, String description) {
        this.description = description;
        this.image = image;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberDesignation() {
        return memberDesignation;
    }

    public int getMemberImage() {
        return memberImage;
    }

    public String getWhatsappNo() {
        return whatsappNo;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getDept() {
        return dept;
    }
}