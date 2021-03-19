package redbull.ecard.UILayer.cards;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

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
    private static boolean ValidityCheck(Card card)
    {
        // Card must be valid in order to be appended
        // Return true if it is valid
        return card != null && card.IsValid();
    }

    // Insert a list of cards to the corresponding view
    public static void InsertToView(Card[] cards, View rootView, @NonNull LayoutInflater inflater, Context context)
    {
        for (int i = 0; i < cards.length; i++)
            InsertToView(cards[i], rootView, inflater, context);
    }

    // Inserts a card to the corresponding view
    public static void InsertToView(Card card, View rootView, @NonNull LayoutInflater inflater, Context context)
    {
        // Invalid views or invalid cards are ignored completely
        if (!ValidityCheck(card) || rootView == null) {
            Log.d ("ERROR", "An Invalid card was not inserted into the view.");
            return;
        }

        // Copies card.xml
        ConstraintLayout child = (ConstraintLayout) inflater.inflate(R.layout.card, null);

        for (int i = 0; i < child.getChildCount(); i++)
        {
            View view = child.getChildAt(i);

            InfoToCard(view, card, context);
        }

        ((LinearLayout) rootView.findViewById(R.id.card_layout)).addView(child);
    }

    // Change the info of the view to the corresponding info on the card
    // TODO throw an exception if any view has the correct tag but incorrect type
    private static void InfoToCard(View child, Card card, Context context)
    {
        String tag = (String)child.getTag();

        if (tag == context.getString(R.string.name_tag)) {
            // View is a name
            ((TextView)child).setText(card.getName().toString());
        }
        else if (tag == context.getString(R.string.description_tag))
        {
            // View is a description
            ((TextView)child).setText(card.getDescription());
        }
        else if (tag == context.getString(R.string.phone_tag))
        {
            // View is a phone
            ((TextView)child).setText(card.getContact().getCellPhone());
        }
        else if (tag == context.getString(R.string.email_tag))
        {
            // View is a email
            ((TextView)child).setText(card.getContact().getEmailAddress());
        }
        else if (tag == context.getString(R.string.template_tag))
        {
            // View is template
            ((ImageView)child).setImageResource(GrabTemplate(card.getTemplateNum()));
        }
    }

    // Retrieve the template resource from its template number
    private static int GrabTemplate(int cardTemplateNum)
    {
        int ret = -1; // Invalid template

        switch (cardTemplateNum)
        {
            case 0:
                ret = R.drawable.template1;
                break;
            case 1:
                ret = R.drawable.template2v2;
        }

        return ret;
    }
}
