package org.gandroid.motif;


import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Methods_FamilyPlanning_brief extends Fragment {
    TextView FPdesc,FPtitle;
    WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_methods, container, false);

        //FPdesc= rootView.findViewById(R.id.FPdesc);
       // FPtitle= rootView.findViewById(R.id.FPTitle);
        //FPdesc.setMovementMethod(new ScrollingMovementMethod());

        webView = rootView.findViewById(R.id.FPBriefsumm);
        webView.loadUrl("file:///android_asset/familyplanning.html");
        webView.setBackgroundColor(Color.TRANSPARENT);

        webView .getSettings().setLoadWithOverviewMode(true);
        webView .getSettings().setUseWideViewPort(true);
      
        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());

        /**
        FPtitle.setText("Family Planning");
        StringBuilder stringBuilder = new StringBuilder();
        String someMsg = " This some random message to check if this is working. ";

        for (int i = 0;i<100;i++){
            stringBuilder.append(someMsg);
        }
        FPdesc.setText(stringBuilder.toString());
        String FPcontent = "Family planning is the practice of controlling the number of " +
                "children in a family and the intervals between their births. Contemporary notions of family" +
                "planning, however, tend to place a woman and her childbearing decisions at the center of the discussion," +
                "as notions of womens empowerment and reproductive autonomy have gained traction in many parts of the" +
                "world. Family planning may involve consideration of the number of children a woman wishes to have," +
                "including the choice to have no children, as well as the age at which she wishes to have them." +
                "These matters are influenced by external factors such as marital situation, career considerations," +
                "financial position, any disabilities that may affect their ability to have children and raise them," +
                "besides many other considerations." +
                "\n" +
                "\n" +
                "If sexually active, family planning may involve the use of contraception" +
                "and other techniques to control the timing of reproduction. Other techniques commonly used include sexuality education," +
                "prevention and management of sexually transmitted infections,pre-conception counseling and management, and infertility management." +
                "\n" +
                "\n" +
                "Family planning is sometimes used as a synonym or euphemism for access to and the use of contraception. However," +
                "it often involves methods and practices in addition to contraception. Additionally, there are many who might wish" +
                "to use contraception but are not, necessarily, planning a family (e.g., unmarried adolescents, young married couples" +
                "delaying childbearing while building a career); family planning has become a catch-all phrase for much of the work" +
                "undertaken in this realm. It is most usually applied to a female-male couple who wish to limit the number of children" +
                "they have and/or to control the timing of pregnancy (also known as spacing children). Family planning may encompass sterilization," +
                "as well as abortion." +
                "\n" +
                "\n Do you know when your last menstrual period began or how long it lasted? If not, it might be time to" +
                "start paying attention." +
                "\n" +
                "Tracking your menstrual cycles can help you understand what's normal for you," +
                "time ovulation and identify important changes — such as a missed period or unpredictable menstrual bleeding." +
                "While menstrual cycle irregularities usually aren't serious, sometimes they can signal health problems." +
                "\n" +
                "\n" +
                "<b>What's the menstrual cycle?</b>" +
                "\n" +
                "The menstrual cycle is the monthly series of changes a woman's body goes through in preparation for the possibility of pregnancy." +
                "Each month, one of the ovaries releases an egg — a process called ovulation. At the same time, hormonal changes prepare" +
                "the uterus for pregnancy. If ovulation takes place and the egg isn't fertilized, the lining of the uterus sheds through" +
                "the vagina. This is a menstrual period." +
                "\n" +
                "\n" +
                "<b>What's normal?</b>" +
                "\n" +
                "The menstrual cycle, which is counted from the first" +
                "day of one period to the first day of the next, isn't the same for every woman. Menstrual flow might occur every 21 to" +
                "35 days and last two to seven days. For the first few years after menstruation begins, long cycles are common." +
                "However, menstrual cycles tend to shorten and become more regular as you age." +
                "\n" +
                "Your menstrual cycle might be regular — about the same length every month — or somewhat irregular, and your period might" +
                "be light or heavy, painful or pain-free, long or short, and still be considered normal. Within a broad range,normal" +
                "is what's normal for you." +
                "\n" +
                "Keep in mind that use of certain types of contraception, such as extended-cycle birth control pills and intrauterine" +
                "devices (IUDs), will alter your menstrual cycle. Talk to your health care provider about what to expect." +
                "\n" +
                "\n" +
                "When you get close to menopause, your cycle might become irregular again. However, because the risk of uterine" +
                "cancer increases as you age, discuss any irregular bleeding around menopause with your health care provider."; **/

        return rootView;
    }
}
