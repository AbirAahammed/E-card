package redbull.ecard.UILayer.cards;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
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
import java.util.ArrayList;

import redbull.ecard.DataLayer.Card;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.CardDatabaseConnector;
import redbull.ecard.R;

// Appends / generates cards to be viewed from the home section
public class CardGenerator {

    // Appends the card to the home view
    // This will stay throughout the apps life time
    private static boolean ValidityCheck(Profile card)
    {
        // Card must be valid in order to be appended
        // Return true if it is valid
        return card != null && card.IsValid();
    }

    // Insert a list of cards to the corresponding view
    public static void InsertToView(ArrayList<Profile> cards, View rootView, @NonNull LayoutInflater inflater, Context context)
    {
        // Do nothing on empty cards list
        if (cards == null)
            return;

        for (int i = 0; i < cards.size(); i++)
            InsertToView(cards.get(i), rootView, inflater, context);
    }

    // Inserts a card to the corresponding view
    public static void InsertToView(Profile card, View rootView, @NonNull LayoutInflater inflater, Context context)
    {
        // Invalid views or invalid cards are ignored completely
        if (!ValidityCheck(card) || rootView == null || context == null) {
            Log.d ("NOTICE", "An Invalid card was not inserted into the view.");
            return;
        }

        // Copies card.xml
        ConstraintLayout child = (ConstraintLayout) inflater.inflate(R.layout.card, null);

        for (int i = 0; i < child.getChildCount(); i++)
        {
            View view = child.getChildAt(i);

            if (view == null)
                continue; // Encase a child has a null reference? Perhaps removed

            InfoToCard(view, card, context);
        }

        ((LinearLayout) rootView.findViewById(R.id.card_layout)).addView(child);
        AddButtonClickEvents(child, context, card.getContact(), rootView);
    }

    // Add button click events to the email/phone icons
    private static void AddButtonClickEvents(View root, Context context, Contact contactInfo, View parentRoot)
    {
        // Email onclick event
        ImageView emailImg = (ImageView)(root.findViewById(R.id.emailIMG));
        emailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == emailImg)
                {
                    // Open email app to send them an email
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String [] { contactInfo.getEmailAddress().toString() });
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Hello! From E-Card");
                    context.startActivity(Intent.createChooser(intent, "Emailing E-Card Contact"));
                }
            }
        });

        // Phone on click event
        ImageView phoneImg = (ImageView)(root.findViewById(R.id.phoneIMG));
        phoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == phoneImg)
                {
                    // Open the phone app to call the number
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + contactInfo.getCellPhone()));
                    context.startActivity(intent);
                }
            }
        });

        // Card expansion on click event
        // If user clicks the background of the card, it will expand to full screen
        ImageView background = (ImageView)(root.findViewById(R.id.cardBackground));
        ScrollView scrollLayout = (ScrollView)parentRoot.findViewById(R.id.cards_scroll);

        background.setOnClickListener(new View.OnClickListener() {
            ViewGroup.LayoutParams initLayout;
            ImageView.ScaleType initType;
            boolean resized = false;
            boolean hasResized = false;

            @Override
            public void onClick(View v) {
                if (v == background)
                {
                    // Full-screen the image view
                    if (!hasResized)
                    {
                        initResize();
                    }

                    else
                    {
                        resizeImage();
                    }
                }
            }

            private void initResize()
            {
                hasResized = true;
                initType = background.getScaleType();
                initLayout = background.getLayoutParams();

                resizeImage();
            }

            private void resizeImage()
            {
                if (!resized) {
                    // Enlarge the card
                    background.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                    background.setScaleType(ImageView.ScaleType.FIT_XY);
                    resized = true;
                }
                else
                {
                    // De-large the card
                    background.setLayoutParams(initLayout);
                    background.setScaleType(initType);
                    resized = false;
                }
            }
        });
    }

    // Change the info of the view to the corresponding info on the card
    // TODO throw an exception if any view has the correct tag but incorrect type
    private static void InfoToCard(View child, Profile card, Context context)
    {
        String tag = (String)child.getTag();

        // Update the corresponding text views based on the provided information of the card
        if (tag == context.getString(R.string.name_tag)) {
            ((TextView)child).setText(card.getName().toString());
        }
        else if (tag == context.getString(R.string.description_tag))
        {
            ((TextView)child).setText(card.getDescription());
        }
        else if (tag == context.getString(R.string.phone_tag))
        {
            ((TextView)child).setText(card.getContact().getCellPhone());
        }
        else if (tag == context.getString(R.string.email_tag))
        {
            ((TextView)child).setText(card.getContact().getEmailAddress());
        }
        else if (tag == context.getString(R.string.service_tag))
        {
            ((TextView)child).setText(card.getService());
        }
        else if (tag == context.getString(R.string.address_tag))
        {
            ((TextView)child).setText(card.getAddress().getFormattedAddress());
        }
        else if (tag == context.getString(R.string.template_tag))
        {
            ((ImageView)child).setImageResource(template (new CardDatabaseConnector().fetchTemplate()));
        }
    }

    // Retrieve the template resource from its template number
    public static int template(int cardTemplateNum)
    {
        int ret = -1; // Invalid template
        Log.d ("Test", "" + cardTemplateNum);
        switch (cardTemplateNum)
        {
            case 1:
                ret = R.drawable.template2v2;
                break;
            case 2:
                ret = R.drawable.template3;
        }

        return ret;
    }
}
