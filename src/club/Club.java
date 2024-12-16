package club;

import java.io.Serializable;

public class Club implements Serializable {
    private String clubName;

    public Club() {
    }

    public Club(String clubName) {
        this.clubName = clubName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @Override
    public String toString() {
        return "Club{" +
                "clubName='" + clubName + '\'' +
                '}';
    }

    public static Club[] dataTest() {
        Club[] clubs = new Club[3];
        clubs[0] = new Club("club1");
        clubs[1] = new Club("club2");
        clubs[2] = new Club("club3");
        return clubs;
    }
}
