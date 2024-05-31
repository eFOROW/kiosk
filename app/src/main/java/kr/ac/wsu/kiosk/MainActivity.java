package kr.ac.wsu.kiosk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.wsu.kiosk.main_fragment.CoffeeFragment;
import kr.ac.wsu.kiosk.main_fragment.SmothieFragment;
import kr.ac.wsu.kiosk.main_fragment.TeaFragment;

public class MainActivity extends AppCompatActivity {
    // RecyclerView 및 Adapter 객체
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<Data> data = new ArrayList<>();

    // Fragment 객체
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
        recyclerView = findViewById(R.id.main_RecyclerView);

        // 초기화 및 데이터 설정
        init();
    }

    private void init() {
        // LinearLayout 매니저 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 어댑터 초기화 및 설정
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        // Button 객체 생성 및 초기화
        Button coffeeBtn = findViewById(R.id.main_coffee_button);
        Button smothieBtn = findViewById(R.id.main_smothie_button);
        Button teaBtn = findViewById(R.id.main_tea_button);
        Button payBtn = findViewById(R.id.mian_payment_Button);

        // FragmentManager 초기화
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Fragment 동적 할당 및 추가
        coffeeFragment = new CoffeeFragment();
        coffeeFragment.setAdapter(adapter);
        smothieFragment = new SmothieFragment();
        smothieFragment.setAdapter(adapter);
        teaFragment = new TeaFragment();

        fragmentTransaction.add(R.id.main_Framelayout, coffeeFragment);
        fragmentTransaction.commit();

        // 버튼 클릭 리스너 설정
        View.OnClickListener listener = v -> {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            int id = v.getId();
            if (id == R.id.main_coffee_button) {
                transaction.replace(R.id.main_Framelayout, coffeeFragment);
            } else if (id == R.id.main_smothie_button) {
                transaction.replace(R.id.main_Framelayout, smothieFragment);
            } else if (id == R.id.main_tea_button) {
                transaction.replace(R.id.main_Framelayout, teaFragment);
            } else if (id == R.id.mian_payment_Button) {
                // 결제 버튼 클릭 시 동작
            }
            transaction.commit();
        };

        // 버튼에 리스너 설정
        coffeeBtn.setOnClickListener(listener);
        smothieBtn.setOnClickListener(listener);
        teaBtn.setOnClickListener(listener);
        payBtn.setOnClickListener(listener);

        // 장바구니(카트) 초기화
        ImageView cart_iv = findViewById(R.id.cart_ImageView);
        TextView cartget_tv = findViewById(R.id.cart_get_TextView);
        count_tv = findViewById(R.id.cart_count_TextView);
        cart_price_TextView = findViewById(R.id.cart_price_TextView);

        // StartActivity에서 데이터 전달 받음
        String info = getIntent().getStringExtra("info");
        if (info != null) {
            if (info.equals("pickup")) {
                cart_iv.setImageResource(R.drawable.pickup_icon);
                cartget_tv.setText("포장");
            } else {
                cart_iv.setImageResource(R.drawable.sit_icon);
                cartget_tv.setText("매장");
            }
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataViewHolder> {

        @NonNull
        @Override
        public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new DataViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
            Data currentItem = data.get(position);

            holder.name_tv.setText(currentItem.name);
            holder.price_tv.setText("가격 : " + currentItem.price + "원");
            holder.option_tv.setText("옵션 : " + currentItem.option);
            holder.count_tv.setText(currentItem.count+"");
            holder.imageView.setImageResource(currentItem.imageResourceId);
            if (currentItem.temperature.equals("Ice")) holder.temp_iv.setImageResource(R.drawable.ice_icon);
            else holder.temp_iv.setImageResource(R.drawable.hot_icon);

            holder.add_Btn.setOnClickListener(v -> {
                int adapterPosition = holder.getAdapterPosition();
                data.get(adapterPosition).count++;
                updateItemCount();
                updateCartPrice();
                notifyItemChanged(adapterPosition);
            });

            holder.minus_Btn.setOnClickListener(v -> {
                int adapterPosition = holder.getAdapterPosition();
                if (data.get(adapterPosition).count > 1) {
                    data.get(adapterPosition).count--;
                    updateItemCount();
                    updateCartPrice();
                    notifyItemChanged(adapterPosition);
                }
            });

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

        // 아이템 추가
        public void addItem(Data d) {
            data.add(d);
            notifyItemInserted(data.size() - 1);
            updateItemCount();
            updateCartPrice();  // 추가 시 총 가격 업데이트
        }

        // 아이템 제거
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

        // 아이템 개수 업데이트
        private void updateItemCount() {
            int totalCount = 0;
            for (Data item : data) {
                totalCount += item.count;
            }
            count_tv.setText(totalCount + "개");
        }

        // 장바구니 총 가격 업데이트
        private void updateCartPrice() {
            int totalPrice = 0;
            for (Data item : data) {
                totalPrice += item.price * item.count;
            }
            cart_price_TextView.setText("총 가격: " + totalPrice + "원");
        }

        // ViewHolder 클래스
        class DataViewHolder extends RecyclerView.ViewHolder {
            TextView name_tv, price_tv, option_tv, count_tv;
            ImageView imageView, temp_iv;
            Button add_Btn, minus_Btn, delete_Btn;

            DataViewHolder(View view) {
                super(view);
                name_tv = view.findViewById(R.id.item_name_textView);
                price_tv = view.findViewById(R.id.item_price_textView);
                count_tv = view.findViewById(R.id.item_count_TextView);
                option_tv = view.findViewById(R.id.item_option_textView);
                imageView = view.findViewById(R.id.item_image_imageView);
                temp_iv = view.findViewById(R.id.item_temp_ImageView);
                add_Btn = view.findViewById(R.id.item_add_Button);
                minus_Btn = view.findViewById(R.id.item_minus_Button);
                delete_Btn = view.findViewById(R.id.itme_delete_Button);
            }
        }
    }
}
