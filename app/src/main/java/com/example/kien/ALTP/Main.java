package com.example.kien.ALTP;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kien.ALTP.Object.ObjectMusic;
import com.example.kien.ALTP.Object.ObjectQuestion;

public class Main extends AppCompatActivity {

    private long mBackPressed = 0;
    private Button btnPlay, btnHighScore;
    private final static int PLAY_CODE = 1;
    private ObjectMusic music;
    private SharedPreferences settings;
    final String PREFS_NAME = "MyPrefsFile";
    MyFunction my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHighScore = (Button) findViewById(R.id.score);
        btnPlay = (Button) findViewById(R.id.play);
        music = new ObjectMusic(Main.this);

        settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("my_first_time", true)) {
            addQuestion();
            settings.edit().putBoolean("my_first_time", false).commit();
        }

        ButtonControl();
        music.setCurrSong(music.play(ObjectMusic.START));
        my = new MyFunction(Main.this) {
            @Override
            public void functionOK(int code) {
                switch (code) {
                    case 1:
                        music.play(ObjectMusic.STOP);
                        Intent i = new Intent(Main.this, Play.class);
                        startActivityForResult(i, PLAY_CODE);
                        break;
                    case 2:
                        finish();
                        break;
                }
            }

            @Override
            public void functionCancel(int code) {
                switch (code){
                    case 1:
                        music.setCurrSong(music.play(ObjectMusic.START));
                        break;
                }
            }
        };
    }

    //    public void addQuestion() {
//        DB_Question db = new DB_Question(Main.this);
//
//        db.addQuestion("In what year did Yugioh first appear in Japan and in what form?", "1996 as a manga", "1999 as an anime", "1999 as a manga", "1996 as an anime", 1);
//        db.addQuestion("In Yugioh anime,what is the pharaoh's real name?", "Atem", "Yami Yugi", "Yugi", "Osiris", 1);
//        db.addQuestion("Who is the Creator of Yugioh?", "Kazuki Takahashi", "Masashi Kishimoto", "Akiza Hushioni", "Kanami Tanakusi", 1);
//        db.addQuestion("In Yugioh anime, in the battle of the Pharaoh VS Yugi, what was the last card to attack and who used it?", "Silent Magician: Yugi", "Dark Magician: Yugi", "Silent Magician: Pharaoh", "Dark Magician: Pharaoh", 1);
//        db.addQuestion("In Yugioh anime, what monster guards the exit of the maze of the Paradox brother's maze?", "The Gate Guardian", "Black Magician", "Red Eyes Black Dragon", "Blue Eyes Ultimate Dragon", 1);
//        db.addQuestion("In Yugioh game, if you manage to gather all of the Exodia cards in your hand, what happens?", "you win and your opponent loses", "it will be a tie game", "you lose and your opponent wins", "nothing happens", 1);
//        db.addQuestion("In Yugioh game, what level is Gaia the Fierce Knight?", "7","6","8","5", 1);
//        db.addQuestion("In Yugioh anime, what happened to Seto Kaiba after he lost to Pegasus in a duel?", "his soul got captured", "he became his slave", "nothing", "he was thrown into a dungeon", 1);
//        db.addQuestion("In Yugioh anime, which card does Joey get after he beats Rex in a duel?", "Red Eyes Black Dragon", "Copy Cat", "Blue Eyes White Dragon", "Summoned Skull", 1);
//
//        db.addQuestion("In Yugioh anime, who are the first two duelists in the semi-final rounds?", "Mai and Yugi", "Yugi and Joey", "Bandit Keith and Yugi", "Joey and Mai", 2);
//        db.addQuestion("In Yugioh anime, in the Duel of Yugi VS Pegasus, what was the first card to be played?", "Horn of the unicorn.", "Toon world", "Red archery girl", "Magical Hats", 2);
//        db.addQuestion("In Yugioh anime, what was the name of Pegasus' love?", "Cicilia", "Saphire", "Selene", "Serena", 2);
//        db.addQuestion("Yugioh is a spin-off of what anime/manga series?", "Pokemon", "Digimon", "Bakugan", "Dragon Ball", 2);
//        db.addQuestion("In Yugioh anime, what card does Seto Kaiba start off with on his first duel with Yugi?", "Hitotsu-Me Giant", "Blue Eyes White Dragon","Mystic Horseman", "The Wicked Worm Beast", 2);
//        db.addQuestion("In Yugioh anime, what is the defense power of Silver Fang?", "800", "500", "600", "700", 2);
//        db.addQuestion("In Yugioh anime, what type of deck does Bandit Keith have?", "Machine", "Dark", "Fighter", "Fiend", 2);
//
//
//        db.addQuestion("What was the first card ever introduced and remains the favorite of the game's creator?", "BlueEyes White Dragon", "Black Magician", "Kuriboh", "The RedEyes Black Dragon", 3);
//        db.addQuestion("In the last episode that the Pharaoh was in, what were yugi's  final words to him?", "Like we always say, It's your move.", "Everything you've given us stays right hear in our hearts.", "No matter what, we will always be a part of eachother.", "We'll never truly be apart.", 3);
//        db.addQuestion("In Yugioh anime, what does Mai do to say she lost?", "Puts her hand on her deck", "Runs out of cards to play", "Tells Yugi she gives up", "Her life points go down to zero", 3);
//        db.addQuestion("In Yugioh anime, how many life points does Yugi have left when he finally lets Yami Yugi duel Mai?", "300", "350","400", "600", 3);
//        db.addQuestion("In Yugioh anime, which of these cards did Seto Kaiba NOT have in his deck for the Battle City Tournament?", "Z Metal Caterpillar", "Anti-Magic Viral Cannon", "Devil Franken", "Blood Vors", 3);
//        db.addQuestion("In Yugioh anime, What color of the dueling arena does Yugi always duel on?", "blue", "red", "black", "green", 3);
//    }
    public void addQuestion() {
        Database db = new Database(Main.this);

        db.addQuestion(new ObjectQuestion("Trong  truyên ngắn” ông lão đánh cá và con cá vàng ” , ông lão đã ra biển mấy lần ?", "6", "4", "5", "3", 2));
        db.addQuestion(new ObjectQuestion("Ai Là Người đầu tiên  chứng minh trái đất có dạng hình cầu ?", "Aristotle", "Copecnich", "Acsimet", "Galileo", 3));
        db.addQuestion(new ObjectQuestion("Ở Mắt bão không khí  chuyển động  như thế nào ?", "Không Chuyển Động", "Theo chiều kim đồng hồ", "Ngược Chiều kim Đồng Hồ", "Tuỳ vào Cơn Bão", 1));
        db.addQuestion(new ObjectQuestion("Ai Đã khắc chữ ” Nam  thiên đệ Nhất động “lên của động hương tích ?", "Chúa Trịnh Sâm", "Chu Mạnh Trinh", "Vua Bảo Đại", "Nam Phương Hoàng hậu", 2));
        db.addQuestion(new ObjectQuestion("Hồ nước ngọt nào lớn nhất theo thê tích và sâu nhất thê giới ?", "Hồ  Baikal", "Hồ  Victoria", "Hồ Ladoga", "Hồ  Lhagba Pool", 2));
        db.addQuestion(new ObjectQuestion("Nơi có hải cảng sầm uất nhất thế giới , bạn cho biết tên  thành phố này ?", "Thượng Hải", "London", "New York", "Trân Châu Cảng", 1));
        db.addQuestion(new ObjectQuestion("Con Sên Có Mấy cài mũi ?", "4", "3", "2", "1", 2));
        db.addQuestion(new ObjectQuestion("Ông tổ  Của ” Đờn ca tài tử ” ?", "Cao Văn lầu", "Nguyễn văn Chánh", "Cao Bá Long", "Nguyễn Thượng Hiền", 3));
        db.addQuestion(new ObjectQuestion("CLb Real Madrid Đã  bao nhiêu lần vô địch  Cúp C1  Châu âu ?", "9", "3", "5", "8", 3));
        db.addQuestion(new ObjectQuestion("Hiên tưởng  khi mặt trời , trái đất  và mặt trăng  theo thứ tự  cùng  nằm trên đường thẳng?", "Nguyệt Thực", "Hoàng Hôn", "Giao Thừa", "Nhật Thực", 1));

        db.addQuestion(new ObjectQuestion("Cây Ca cao có nguồn gốc  từ châu lục nào ?", "Châu Mỹ", "Châu Phi", "Châu Á", "Châu Âu", 1));
        db.addQuestion(new ObjectQuestion("Năm 1928  , nhân vật hoạt hình nào lần đầu tiên xuất hiễn ?", "Chuột Mickey", "Vit Donald", "Thuỷ  Thủ Popeye", "Tom & jerry", 2));
        db.addQuestion(new ObjectQuestion("Núi lửa hoạt động lớn nhất thế giới hiện nay nằm ở đâu ?", "Quần Đảo hawail", "Nhật Bản", "Philippines", "Indonesia", 2));
        db.addQuestion(new ObjectQuestion("Văn Miếu , trường đại học đầu tiên của việt nam , được xây dựng dưới  triều đại nào ?", "Nhà Họ Lý", "Nhà Họ Trần", "Nhà Họ Ngô", "Nhà Họ Nguyễn", 1));
        db.addQuestion(new ObjectQuestion("Đất nước nào trên thế giới có hình dạng giống Quả ớt ?", "Chile", "Cuba", "Brazil", "Lào", 1));
        db.addQuestion(new ObjectQuestion("Hoàng hậu Mari Antoanet  Của Nước pháp  la vợ Của vua nào ?", "Luis 16", "Luis 13", "Luis 15", "Luis 14", 1));
        db.addQuestion(new ObjectQuestion("Vị Trạng Nguyên Nhỏ tuổi nhất trong lịch sử Việt Nam ?", "Nguyễn Hiền", "Lê Văn Thịnh", "Vũ Tuấn Chiêu", "Nguyễn Kì", 2));
        db.addQuestion(new ObjectQuestion("Sông Mekong bắt nguồn từ đâu ?", "Trung Quốc", "Lào", "Việt Nam", "Campuchia", 1));


        db.addQuestion(new ObjectQuestion("Đội Bóng xếp thứ 3 giải  Ngoại hạng anh mùa bóng 2006-2007?", "Liverpool", "Manchester United", "Asenal", "Chelsea", 3));
        db.addQuestion(new ObjectQuestion("Bộ tem  bưu chính  đầu tiên của Việt Nam in Hình gì  ?", "Chân Dung Hồ Chí Minh", "Hình Bông Sen", "Hình Chùa một Cột", "Hình Hồ Gươm", 3));
        db.addQuestion(new ObjectQuestion("Điều khiển từ xa được phát minh vào năm nào ?", "1898", "1838", "1788", "1981", 3));
        db.addQuestion(new ObjectQuestion("Đỉnh núi Cao Nhất nước ta  Pan Xi păng Thuộc tỉnh nào ?", "Lào Cai", "Sơn La", "Cao Bằng", "Bắc Cạn", 2));
        db.addQuestion(new ObjectQuestion("Quốc Gia  Có Lượng Khí thải lớn Nhất Thế Giới ?", "Australia", "Đức", "Mỹ", "Anh", 3));
        db.addQuestion(new ObjectQuestion("Nguyên tử cacbon có bao nhiêu electron ?", "6", "4", "2", "12", 1));
        db.addQuestion(new ObjectQuestion("Trong thế kỷ XX  nước tây ban nha tổ chức worlcup vào năm nào ?", "1982", "1988", "1991", "1983", 3));
        db.addQuestion(new ObjectQuestion("Hai câu thơ ” Chốn xưa xe ngựa hồn thu thảo.đền cũ lâu đài bóng tịch dương là của  tác giả nào ?", "Bà Huyện Thanh Quan", "Hồ Xuân Hương", "Đoàn Thị Điểm", "Thâm Tâm", 2));
        db.addQuestion(new ObjectQuestion("Đội tuyển Bóng đá Nước nào Vô Địch Euro năm 1976 ?", "Không phải 3 Đội Trên", "Đức", "Ý", "Pháp", 1));
        db.addQuestion(new ObjectQuestion("Đại dương có diện tích nhỏ nhất trên thế giới ?", "Bắc Băng Dương", "Đại tây Dương", "Ấn Độ Dương", "Thái Bình Dương", 1));
        db.addQuestion(new ObjectQuestion("Bạn Cho Biết từ năm 2001, mức lương tồng thống mỹ là bao nhiêu USD ?", "400 Ngàn USD", "500 Ngàn USD", "300 Ngàn USD", "600 Ngàn USD", 2));
        db.addQuestion(new ObjectQuestion("Biển nội  Địa có diện tích rộng nhất ?", "Địa Trung Hải", "Biển Caspi", "Biển Hồng Hải", "Thái Bình Dương", 2));
    }

    public void ButtonControl() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.setCurrSong(music.play(ObjectMusic.READY));
                my.showDialog( MyFunction.READY, MyFunction.YES, MyFunction.NO, 1);

            }
        });

        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.play(ObjectMusic.STOP);
                Intent i = new Intent(Main.this, HighScore.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY_CODE)
            if (resultCode == 0) {
                music.setCurrSong(music.play(ObjectMusic.START));
                Intent i = new Intent(Main.this, HighScore.class);
                startActivity(i);
            }
    }

    @Override
    public void onBackPressed() {
        my.showDialog( MyFunction.EXIT, MyFunction.YES, MyFunction.NO, 2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        music.play(ObjectMusic.STOP);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        music.setCurrSong(music.play(music.getCurrSong()));
    }
}
