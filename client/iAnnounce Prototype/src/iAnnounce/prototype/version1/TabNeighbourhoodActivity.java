package iAnnounce.prototype.version1;

import android.content.Intent;
import android.os.Bundle;

public class TabNeighbourhoodActivity extends TabGroupActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startChildActivity("OptionsActivity", new Intent(this,Community.class));
    }
}
