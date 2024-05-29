// File path: kr/ac/wsu/kiosk/MainActivity.java

package kr.ac.wsu.kiosk;

import static android.text.TextUtils.replace;

import android.annotation.SuppressLint;
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
    TextView count_tv, cart_price_TextView;

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

                }
            }
        };
        coffeeBtn.setOnClickListener(listener);
        smothieBtn.setOnClickListener(listener);
        teaBtn.setOnClickListener(listener);
        payBtn.setOnClickListener(listener);

        // 장바구니(카트) 초기화
        ImageView cart_iv = findViewById(R.id.cart_ImageView);
        TextView cartget_tv = findViewById(R.id.cart_get_TextView);
        count_tv = findViewById(R.id.cart_count_TextView);
        cart_price_TextView = findViewById(R.id.cart_price_TextView);

        String info = getIntent().getStringExtra("info"); // StartActivity에서 데이터 전달 받음
        assert info != null;
        if (info.equals("pickup")){
            cart_iv.setImageResource(R.drawable.pickup_icon);
            cartget_tv.setText("포장");
        } else {
            cart_iv.setImageResource(R.drawable.sit_icon);
            cartget_tv.setText("매장");
        }
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
        adapter.addItem(new Data("아메리카노", 2500, null, "https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708"));
        adapter.addItem(new Data("아메리카노", 2500, "얼음 많이, 시럽 조금", "https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708"));
        adapter.addItem(new Data("아메리카노", 2500, null, "https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708"));
        adapter.addItem(new Data("블루베리스무디", 3500, null, "https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708"));
        adapter.addItem(new Data("아메리카노", 2500, null, "https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708"));
        adapter.addItem(new Data("아메리카노", 2500, null, "https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708"));
        adapter.addItem(new Data("아메리카노", 2500, "시럽 조금", "https://composecoffee.com/files/thumbnails/451/038/384x530.crop.jpg?t=1708913708"));
    }

    private void updateCartPrice() {
        int totalPrice = 0;
        for (Data item : data) {
            totalPrice += item.price;
        }
        cart_price_TextView.setText("총 가격: " + totalPrice + "원");
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataViewHolder> {

        @NonNull
        @Override
        public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new DataViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
            Data currentItem = data.get(position);

            holder.name_tv.setText(currentItem.name);
            holder.price_tv.setText("가격 : " + currentItem.price);
            holder.option_tv.setText("옵션 : " + currentItem.option);

            Glide.with(holder.itemView.getContext())
                    .load(currentItem.imageUrl)
                    .into(holder.imageView);

            holder.delete_Btn.setOnClickListener(v -> {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    removeItem(adapterPosition);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        void addItem(Data d) {
            data.add(d);
            notifyItemInserted(data.size() - 1);
            updateItemCount();
            updateCartPrice();  // 추가 시 총 가격 업데이트
        }

        void removeItem(int position) {
            if (position >= 0 && position < data.size()) {
                data.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, data.size());
                updateItemCount();
                updateCartPrice();  // 삭제 시 총 가격 업데이트
            } else {
                Toast.makeText(MainActivity.this, "Invalid position: " + position + " / " + data.size(), Toast.LENGTH_SHORT).show();
            }
        }

        private void updateItemCount() {
            count_tv.setText(data.size() + "개");
        }

        class DataViewHolder extends RecyclerView.ViewHolder {
            TextView name_tv, price_tv, option_tv;
            ImageView imageView;
            Button delete_Btn;

            DataViewHolder(View view) {
                super(view);
                name_tv = view.findViewById(R.id.item_name_textView);
                price_tv = view.findViewById(R.id.item_price_textView);
                option_tv = view.findViewById(R.id.item_option_textView);
                imageView = view.findViewById(R.id.item_image_imageView);
                delete_Btn = view.findViewById(R.id.itme_delete_Button);
            }
        }
    }

}
