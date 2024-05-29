// File path: kr/ac/wsu/kiosk/MainActivity.java

package kr.ac.wsu.kiosk;

import static android.text.TextUtils.replace;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kr.ac.wsu.kiosk.main_fragment.CoffeeFragment;
import kr.ac.wsu.kiosk.main_fragment.SmothieFragment;
import kr.ac.wsu.kiosk.main_fragment.TeaFragment;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<Data> data = new ArrayList<>();
    private CoffeeFragment coffeeFragment;
    private SmothieFragment smothieFragment;
    private TeaFragment teaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 엣지 투 엣지 디스플레이 활성화
        EdgeToEdge.enable(this);

        // 액티비티 레이아웃 설정
        setContentView(R.layout.activity_main);

        // 메인 뷰에 윈도우 인셋 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // RecyclerView 객체 초기화
        recyclerView = (RecyclerView) findViewById(R.id.main_RecyclerView);

        // 초기화 및 데이터 설정
        init();
        setData();

        // 시작화면에서 포장 or 매장 선택 정보 받기
        String info = getIntent().getStringExtra("info");
    }

    private void init() {
        // LinearLayout 매니저 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 어댑터 초기화 및 설정
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        // Button 객체 생성
        Button coffeeBtn = (Button) findViewById(R.id.main_coffee_button);
        Button smothieBtn = (Button) findViewById(R.id.main_smothie_button);
        Button teaBtn = (Button) findViewById(R.id.main_tea_button);
        Button payBtn = (Button) findViewById(R.id.mian_payment_Button);

        // FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Fragment 동적 할당
        coffeeFragment = new CoffeeFragment();
        smothieFragment = new SmothieFragment();
        teaFragment = new TeaFragment();

        fragmentTransaction.add(R.id.main_Framelayout, coffeeFragment);
        fragmentTransaction.commit();

        Button.OnClickListener listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 클릭 동작
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                int id = v.getId();
                if (id == R.id.main_coffee_button) {
                    fragmentTransaction.replace(R.id.main_Framelayout, coffeeFragment);
                    fragmentTransaction.commit();
                } else if (id == R.id.main_smothie_button) {
                    fragmentTransaction.replace(R.id.main_Framelayout, smothieFragment);
                    fragmentTransaction.commit();
                } else if (id == R.id.main_tea_button) {
                    fragmentTransaction.replace(R.id.main_Framelayout, teaFragment);
                    fragmentTransaction.commit();
                }else if (id == R.id.mian_payment_Button) {
                    fragmentTransaction.replace(R.id.main_Framelayout, teaFragment);
                    fragmentTransaction.commit();
                }
            }
        };

        coffeeBtn.setOnClickListener(listener);
        smothieBtn.setOnClickListener(listener);
        teaBtn.setOnClickListener(listener);
        payBtn.setOnClickListener(listener);
    }

    public static class Data {
        String name;
        int price;
        String option;
        String imageUrl;

        // 데이터 생성자
        public Data(String name, int price, String option ,String imageUrl) {
            this.name = name;
            this.price = price;
            this.option = option == null ? "없음" : option;
            this.imageUrl = imageUrl;
        }
    }

    private void setData() {
        // 예제 데이터 추가
        Data d = new Data("아메리카노", 2500,null,"https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708");
        adapter.addItem(d);
        d = new Data("아메리카노", 2500, "얼음 많이, 시럽 조금","https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708");
        adapter.addItem(d);
        d = new Data("아메리카노", 2500, null, "https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708");
        adapter.addItem(d);
        d = new Data("아메리카노", 2500, null,"https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708");
        adapter.addItem(d);
        d = new Data("아메리카노", 2500, null,"https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708");
        adapter.addItem(d);
        d = new Data("아메리카노", 2500, null,"https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708");
        adapter.addItem(d);
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // 아이템 레이아웃을 인플레이트하여 뷰홀더 생성
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new DataViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            // 뷰홀더에 데이터 바인딩
            DataViewHolder dataViewHolder = (DataViewHolder) holder;
            Data currentItem = data.get(position);

            dataViewHolder.name_tv.setText(currentItem.name);
            dataViewHolder.price_tv.setText("가격 : " + String.valueOf(currentItem.price));
            dataViewHolder.option_tv.setText("옵션 : " + currentItem.option);

            // 이미지 URL을 사용하여 이미지 로드
            Glide.with(dataViewHolder.itemView.getContext())
                    .load(currentItem.imageUrl)
                    .into(dataViewHolder.imageView);
        }

        @Override
        public int getItemCount() {
            // 아이템의 개수 반환
            return data.size();
        }

        void addItem(Data d) {
            // 데이터 리스트에 아이템 추가
            data.add(d);
        }

        private class DataViewHolder extends RecyclerView.ViewHolder {
            TextView name_tv, price_tv, option_tv;
            ImageView imageView;

            // 뷰홀더 생성자
            public DataViewHolder(View view) {
                super(view);
                name_tv = (TextView) view.findViewById(R.id.item_name_textView);
                price_tv = (TextView) view.findViewById(R.id.item_price_textView);
                option_tv = (TextView) view.findViewById(R.id.item_option_textView);
                imageView = (ImageView) view.findViewById(R.id.item_image_imageView);
            }
        }
    }
}
