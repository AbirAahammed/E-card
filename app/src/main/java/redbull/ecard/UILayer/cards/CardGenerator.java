package redbull.ecard.UILayer.cards;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;

import redbull.ecard.DataLayer.Card;
import redbull.ecard.R;

// Appends / generates cards to be viewed from the home section
public class CardGenerator {

    // Appends the card to the home view
    // This will stay throughout the apps life time
    public static boolean AppendCard(Card card)
    {
        // Card must be valid in order to be appended
        if (card == null || !card.IsValid())
            return false;

        // Append the information of the card to the class


        return true;
    }

    public static void InsertToView(Card card, View parent)
    {
       // View elem = LayoutInflater.from(loc).inflate (R.layout.card_body, null);
       // view.addView(elem);
    }
}
