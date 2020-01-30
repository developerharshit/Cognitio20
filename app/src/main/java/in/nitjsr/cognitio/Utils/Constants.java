package in.nitjsr.cognitio.Utils;

public class Constants {

    public static final String[] EVENT_NAMES = new String[]{
            "Radiation",
            "Wit to Veto",
            "Dictum Symposium",
            "Conundrum",
            "TIE the KNOT",
            "Place Station",
            "CANSYS",
            "Assemblage",
            "Quriosity",
            "Shoot at Sight",
            "Battle of Bureaucrats",
            "Kalaam-E-Kalam",
    };
    public static final String[] EVENT_DESCRIPTION = new String[]{
            "Study of Modern Industrial Technology",
            "Industrial Case Study",
            "Debate on Corporate World Challenges",
            "Open the GATE of your dream",
            "Mechanism Design and Business Plan Presentation",
            "Mock Placement Drive",
            "Modelling, Simulation and Analysis",
            "Bridge Construction and Strength Test",
            "Green Technology Quiz",
            "Water Rocket Competition",
            "Quest for Civil Services",
            "Creative Writing and Presentation"
    };

    public static final String[] EVENT_IMAGE_ONLINE = new String[]{
            "https://firebasestorage.googleapis.com/v0/b/cognitio-15539.appspot.com/o/Events%2Fpaper.jpg?alt=media&token=95520976-63f3-4774-8c5a-60347c02b399",//Radiation
            "https://firebasestorage.googleapis.com/v0/b/cognitio-15539.appspot.com/o/Events%2Fcase%20study.jpg?alt=media&token=f9c0699c-cd65-4c71-b929-3f43ced1bd35",//Study of Scarlet
            "https://firebasestorage.googleapis.com/v0/b/cognitio-15539.appspot.com/o/Events%2Fdebate.jpg?alt=media&token=1793d855-ae8b-4b89-b880-673774dac3fe",//Debate
            "https://firebasestorage.googleapis.com/v0/b/cognitio-15539.appspot.com/o/Events%2Friddlwes.jpg?alt=media&token=de5ec97c-065b-4861-8c97-13661d88946e",//Riddles
            "https://firebasestorage.googleapis.com/v0/b/cognitio-15539.appspot.com/o/Events%2Fenterprenership.jpg?alt=media&token=01ce0254-a242-4ed6-b63f-09b3ae26d412",//Enterpreneur
            "https://firebasestorage.googleapis.com/v0/b/cognitio-15539.appspot.com/o/Events%2Frecruitments.jpg?alt=media&token=68b1f0be-1e79-4bcd-9589-0fa600fea200",//placements
            "https://firebasestorage.googleapis.com/v0/b/cognitio-15539.appspot.com/o/Events%2Fdesign%20and%20modelling.jpg?alt=media&token=880180b0-07ff-4889-a349-c38aecfce2b9",//Catia
            "https://firebasestorage.googleapis.com/v0/b/cognitio-15539.appspot.com/o/Events%2Fbridges1.jpg?alt=media&token=9c3af42f-d25d-4ee2-a69b-c0d44f044b9e",//Bridges
            "https://firebasestorage.googleapis.com/v0/b/cognitio-15539.appspot.com/o/Events%2Fquiz1.jpg?alt=media&token=f9428dd8-b623-4137-b4b2-f33c23824479",//Quiriosity
            "https://firebasestorage.googleapis.com/v0/b/cognitio-15539.appspot.com/o/Events%2FRocket.jpg?alt=media&token=cb858d93-c0e4-4b14-898c-ceccfb54e76c",//Water Rocket
            "https://firebasestorage.googleapis.com/v0/b/cognitio-a1c2c.appspot.com/o/Events%2FBoB.jpg?alt=media&token=04c4b73d-7ca8-4d55-86fd-4b377f3559f9",//Naukrasahi
            "https://firebasestorage.googleapis.com/v0/b/cognitio-a1c2c.appspot.com/o/Events%2Fkek.jpeg?alt=media&token=2d433f54-4785-450f-9ad0-7ffd214b13e7",//kalam e kalam
    };

    public static final int EVENT_FLAG = 1;
    public static final int GURU_GYAN_FLAG = 2;
    public static final int SPONSORS_FLAG = 3;

    public static final String FIREBASE_REF_EMAIL = "email";
    public static final String FIREBASE_REF_NAME = "name";
    public static final String FIREBASE_REF_PHOTO = "photo";
    public static final String FIREBASE_REF_MOBILE = "mobile";
    public static final String FIREBASE_REF_COLLEGE = "college";
    public static final String FIREBASE_REF_COLLEGE_REG_ID = "regID";
    public static final String FIREBASE_REF_TSHIRT_SIZE = "tsirtSize";
    public static final String FIREBASE_REF_KIT = "kit";
    public static final String NOT_REGISTERED = "Not Registered";
    public static final String FIREBASE_REF_TSHIRT = "Tshirt";
    public static final String FIREBASE_REF_PAYMENT = "payment";
    public static final String FIREBASE_REF_COGNITIO_ID = "cognitio_ID";
    public static final String FIREBASE_REF_COLLEGE_CODE = "college_code";

    public static final int FIREBASE_REF_PAYTM_PAYMENT = 1;
    public static final int FIREBASE_REF_DESK_PAYMENT = 2;

    public static final String FIREBASE_REF_PAYTM_CHECKSUM = "checksum";
    public static final String FIREBASE_REF_PAYTM_BANKNAME = "bank name";
    public static final String FIREBASE_REF_PAYTM_TXNID = "txn id";
    public static final String FIREBASE_REF_PAYTM_PAYMENTMODE = "payment mode";
    public static final String FIREBASE_REF_PAYTM_BANKTXNID = "bank txn id";
    public static final String FIREBASE_REF_PAYTM_TXNTIME = "txn time";
    public static final String FIREBASE_REF_PAYTM_GATEWAY = "gateway";
    public static final String FIREBASE_REF_PAYTM_ORDERID = "orderId";
    public static final String FIREBASE_REF_PAYTM_TXN_AMT = "txn amt";
    public static final String FIREBASE_REF_PAYTM = "paytm";

    public static final String FIREBASE_REF_PAID_AMOUNT = "paid_amount";

    public static final String FIREBASE_REF_NOTIFICATIONS = "Notifications";
    public static final String FIREBASE_REF_USERS = "Users";

    public static final String FIREBASE_REF_POSTER_IMAGE = "Poster";
    public static final String FIREBASE_REF_GALLERY = "Galery";
    public static final String FIREBASE_REF_ITINERARY_POSTERS = "Itinerary_Posters";
    public static final String FIREBASE_REF_ITINERARY = "Itinerary";
    public static final String FIREBASE_REF_SPONSORS = "Sponsors";
    public static final String FIREBASE_REF_IMAGES = "Images";
    public static final String FIREBASE_REF_SWITCH = "switch";

    public static final String FIREBASE_REF_PAYMENT_AMT = "Payment_amt";
    public static final String FIREBASE_REF_INSTRUCTION = "Instruction";

}
