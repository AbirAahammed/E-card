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

import redbull.ecard.DataLayer.Card;
import redbull.ecard.DataLayer.Contact;
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
        // Insert the first card twice to resolve the hidden card issue
        InsertToView(cards[0], rootView, inflater, context);

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
                    boolean adjustScroll = false;
                    final int CLOSE_SCROLL_AMOUNT = 1550;
                    Log.d ("Cur", "" + (scrollLayout.getMeasuredHeight() - scrollLayout.getScrollY() ));
                    if (scrollLayout.getMeasuredHeight() - scrollLayout.getScrollY() <= CLOSE_SCROLL_AMOUNT)
                        adjustScroll = true;

                    // Enlarge the card
                    background.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                    background.setScaleType(ImageView.ScaleType.FIT_XY);
                    resized = true;

                    // FIXME does not work atm
                    // The reason is most likely that this call is to early, I need to call it in the next frame after the enlarged image is drawn
                    // To resolve this, I need to wait until the next frame, then perform the action
                    if (adjustScroll)
                        scrollLayout.setScrollY(scrollLayout.getHeight());
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
        Log.d ("Test", "" + cardTemplateNum);
        switch (cardTemplateNum)
        {
            case 0:
                // FIXME need to have the template determined by the users current setting, not the card
                //ret = R.drawable.template1v1;
                ret = R.drawable.template2v2;
                break;
            case 1:
                ret = R.drawable.template2v2;
        }

        return ret;
    }
}
