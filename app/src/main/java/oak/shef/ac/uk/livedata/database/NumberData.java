package oak.shef.ac.uk.livedata.database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity()
public class NumberData {
    @PrimaryKey(autoGenerate = true)
    @android.support.annotation.NonNull

    private int id = 0;
    private int number;
    public NumberData(int number) {
        this.number= number;
    }
    @android.support.annotation.NonNull
    public int getId() {
        return id;
    }
    public void setId(@android.support.annotation.NonNull int id) {
        this.id = id;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}


