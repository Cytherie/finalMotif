package org.gandroid.motif;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MethodsArticle extends AppCompatActivity {

    String [] NAMES = {"Barrier","Hormonal","IUDs","Natural","Permanent"};
    String [] DESCRIPTIONS = {"A method which prevent pregnancy by keeping the sperm from reaching the egg.",
            "which prevent the woman’s ovary from releasing an egg, make it harder for the sperm to reach the egg and keep the lining of the womb from supporting a pregnancy.",
            "which prevent the man’s sperm from fertilizing the woman’s egg.","which help a woman know when she is fertile, so that she can avoid having sex at that time.",
            "These are operations which make it impossible for a man or a woman to have any children."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_methods_article);

        RelativeLayout famplanchoice = (RelativeLayout)findViewById(R.id.Famplansection) ;
        famplanchoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextactivity;

                nextactivity = new Intent(MethodsArticle.this,methodcontent.class);
                nextactivity.putExtra("Key","Family Planning");
                startActivity(nextactivity);

            }
        });

        ListView listView = (ListView)findViewById(R.id.listViewMethods);
        CustomAdapter customAdapter= new CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selection= NAMES[i];
                Intent nextactivity;
                switch (selection){
                    case "Barrier":
                        nextactivity = new Intent(MethodsArticle.this,methodcontent.class);
                        nextactivity.putExtra("Key","Barrier");
                        startActivity(nextactivity);
                        break;


                    case "Hormonal":

                        nextactivity = new Intent(MethodsArticle.this,methodcontent.class);
                        nextactivity.putExtra("Key","Hormonal");
                        startActivity(nextactivity);
                        break;

                    case "IUDs":

                        nextactivity= new Intent(MethodsArticle.this,methodcontent.class);
                        nextactivity.putExtra("Key","IUDs");
                        startActivity(nextactivity);
                        break;

                    case "Natural":
                        nextactivity= new Intent(MethodsArticle.this,methodcontent.class);
                        nextactivity.putExtra("Key","Natural");
                        startActivity(nextactivity);
                        break;

                    case "Permanent":
                        nextactivity= new Intent(MethodsArticle.this,methodcontent.class);
                        nextactivity.putExtra("Key","Permanent");
                        startActivity(nextactivity);
                        break;

                    default:

                        Toast.makeText(MethodsArticle.this,"Sorry, something went wrong :(",Toast.LENGTH_LONG).show();
                        break;

                }

            }
        });
/**
        TextView title = (TextView) findViewById(R.id.FPtitle);
        TextView content = (TextView) findViewById(R.id.FPBriefsumm);

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
                "What's the menstrual cycle?" +
                "\n" +
                "The menstrual cycle is the monthly series of changes a woman's body goes through in preparation for the possibility of pregnancy." +
                "Each month, one of the ovaries releases an egg — a process called ovulation. At the same time, hormonal changes prepare" +
                "the uterus for pregnancy. If ovulation takes place and the egg isn't fertilized, the lining of the uterus sheds through" +
                "the vagina. This is a menstrual period." +
                "\n" +
                "\n" +
                "What's normal?" +
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
                "cancer increases as you age, discuss any irregular bleeding around menopause with your health care provider.";

        title.setText("Family Planning");
        content.setText(FPcontent); **/
    }

    class  CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return NAMES.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayoutmethods,null);
            TextView textviewname = (TextView)view.findViewById(R.id.Titlename);
            TextView textviewdesc = (TextView)view.findViewById(R.id.contents);

            textviewname.setText(NAMES[i]);
            textviewdesc.setText(DESCRIPTIONS[i]);
            return view;
        }
    }
}
