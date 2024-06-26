package kr.ac.wsu.kiosk.main_fragment;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.ac.wsu.kiosk.Data;
import kr.ac.wsu.kiosk.MainActivity;
import kr.ac.wsu.kiosk.R;

public class CoffeeFragment extends Fragment {

    AlertDialog dialog;
    String menu;
    TextView menuPrice_TextView, menuTextView;
    int img_id; // 이미지 id

    @SuppressLint("UseRequireInsteadOfGet")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_coffee, container, false);

        // 버튼 객체 초기화
        Button americanoBtn = fragmentView.findViewById(R.id.americano_Button);
        Button cafe_latteBtn = fragmentView.findViewById(R.id.cafe_latte_Button);
        Button cafe_mochaBtn = fragmentView.findViewById(R.id.cafe_mocha_Button);
        Button vanilla_latteBtn = fragmentView.findViewById(R.id.vanilla_latte_Button);
        Button espressoBtn = fragmentView.findViewById(R.id.espresso_Button);

        // 버튼 클릭 리스너 설정
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.americano_Button) {
                    menu = "americano";
                    showDialog();
                } else if (id == R.id.cafe_latte_Button) {
                    menu = "cafe_latte";
                    showDialog();
                } else if (id == R.id.cafe_mocha_Button) {
                    menu = "cafe_mocha";
                    showDialog();
                } else if (id == R.id.vanilla_latte_Button) {
                    menu = "vanilla_latte";
                    showDialog();
                } else if (id == R.id.espresso_Button) {
                    menu = "espresso";
                    showDialog();
                }
            }
        };

        // 모든 버튼에 리스너 설정
        americanoBtn.setOnClickListener(buttonClickListener);
        cafe_latteBtn.setOnClickListener(buttonClickListener);
        cafe_mochaBtn.setOnClickListener(buttonClickListener);
        vanilla_latteBtn.setOnClickListener(buttonClickListener);
        espressoBtn.setOnClickListener(buttonClickListener);

        return fragmentView;
    }

    private Button selectedTemperatureButton; // 현재 선택된 온도 옵션 버튼을 추적하기 위한 변수
    private Button selectedSyrupButton; // 현재 선택된 시럽 옵션 버튼을 추적하기 위한 변수
    private Button selectedWhippingButton; // 현재 선택된 휘핑 옵션 버튼을 추적하기 위한 변수
    private String temperature, syrup, whipping; // 선택된 온도와 시럽, 휘핑을 저장하는 변수

    private void showDialog() {
        // AlertDialog.Builder를 사용하여 Custom Dialog 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.option_dialog, null);
        builder.setView(dialogView);

        // 온도 선택
        Button hotBtn = dialogView.findViewById(R.id.option_hotButton);
        Button iceBtn = dialogView.findViewById(R.id.option_iceButton);

        // 시럽 선택
        Button littleSyrupBtn = dialogView.findViewById(R.id.option_littleSyrupButton);
        Button normalSyrupBtn = dialogView.findViewById(R.id.option_normalSyrupButton);
        Button muchSyrupBtn = dialogView.findViewById(R.id.option_muchSyrupButton);

        //휘핑 선택
        Button noWhippingBtn = dialogView.findViewById(R.id.option_noWhippingButton);
        Button littleWhippingBtn = dialogView.findViewById(R.id.option_littleWhippingButton);
        Button normalWhippingBtn = dialogView.findViewById(R.id.option_normalWhippingButton);
        Button muchWhippingBtn = dialogView.findViewById(R.id.option_muchWhippingButton);

        // 선택 완료, 취소
        Button doneBtn = dialogView.findViewById(R.id.option_selectButton);
        Button cancelBtn = dialogView.findViewById(R.id.option_cancelButton);

        // 선택 메뉴 출력
        ImageView menuImageView = dialogView.findViewById(R.id.option_image_imageView);
        menuTextView = dialogView.findViewById(R.id.option_name_textView);
        menuPrice_TextView = dialogView.findViewById(R.id.option_price_textView);
        setOption_init(menuImageView, menuTextView, menuPrice_TextView,menu);

        // 기본 선택값으로 ice를 설정합니다.
        selectTemperatureOption(iceBtn, "Ice");
        // 기본 선택값으로 normal을 설정합니다.
        selectSyrupOption(normalSyrupBtn, "보통");
        // 기본 선택값으로 no를 설정
        selectWhippingOption(noWhippingBtn, "없이");

        // 버튼 클릭 리스너 설정
        hotBtn.setOnClickListener(optionButtonClickListener);
        iceBtn.setOnClickListener(optionButtonClickListener);
        littleSyrupBtn.setOnClickListener(optionButtonClickListener);
        normalSyrupBtn.setOnClickListener(optionButtonClickListener);
        muchSyrupBtn.setOnClickListener(optionButtonClickListener);
        noWhippingBtn.setOnClickListener(optionButtonClickListener);
        littleWhippingBtn.setOnClickListener(optionButtonClickListener);
        normalWhippingBtn.setOnClickListener(optionButtonClickListener);
        muchWhippingBtn.setOnClickListener(optionButtonClickListener);
        doneBtn.setOnClickListener(optionButtonClickListener);
        cancelBtn.setOnClickListener(optionButtonClickListener);

        // Dialog 외부를 터치했을 때 Dialog가 닫히지 않도록 설정
        builder.setCancelable(false);

        // AlertDialog 생성
        dialog = builder.create();

        // Dialog를 화면에 표시
        dialog.show();
    }

    private void setOption_init(ImageView menuImageView, TextView menuTextView, TextView menuPrice_TextView, String menu) {
        switch (menu){
            case "americano":
                menuImageView.setImageResource(R.drawable.americano);
                img_id = R.drawable.americano;
                menuTextView.setText("아메리카노");
                menuPrice_TextView.setText("3000원");
                break;
            case "cafe_latte":
                menuImageView.setImageResource(R.drawable.latte);
                img_id = R.drawable.latte;
                menuTextView.setText("카페라떼");
                menuPrice_TextView.setText("4200원");
                break;
            case "cafe_mocha":
                menuImageView.setImageResource(R.drawable.mocha);
                img_id = R.drawable.mocha;
                menuTextView.setText("카페모카");
                menuPrice_TextView.setText("4500원");
                break;
            case "vanilla_latte":
                menuImageView.setImageResource(R.drawable.vanilla_latte);
                img_id = R.drawable.vanilla_latte;
                menuTextView.setText("바닐라라떼");
                menuPrice_TextView.setText("4500원");
                break;
            case "espresso":
                menuImageView.setImageResource(R.drawable.espresso);
                img_id = R.drawable.espresso;
                menuTextView.setText("에스프레소");
                menuPrice_TextView.setText("3000원");
                break;
        }
    }

    private MainActivity.RecyclerViewAdapter adapter; // MainActivity의 adapter를 참조하기 위한 변수

    // MainActivity에서 adapter를 설정하는 메서드
    public void setAdapter(MainActivity.RecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    // 데이터를 추가하는 메서드
    private void addItemToAdapter() {
        if (adapter != null) {
            // MainActivity의 adapter에 데이터 추가
            String menu = menuTextView.getText().toString();
            int price = Integer.parseInt(menuPrice_TextView.getText().toString().replace("원", ""));
            String option = "시럽 " + syrup + ", 휘핑 " + whipping;

            adapter.addItem(new Data(menu, temperature, price, option, img_id, 1));
        }
    }

    // 옵션 버튼 클릭 리스너
    private View.OnClickListener optionButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.option_hotButton) {
                selectTemperatureOption((Button) v, "Hot");
            } else if (v.getId() == R.id.option_iceButton) {
                selectTemperatureOption((Button) v, "Ice");
            } else if (v.getId() == R.id.option_littleSyrupButton) {
                selectSyrupOption((Button) v, "조금");
            } else if (v.getId() == R.id.option_normalSyrupButton) {
                selectSyrupOption((Button) v, "보통");
            } else if (v.getId() == R.id.option_muchSyrupButton) {
                selectSyrupOption((Button) v, "많이");
            } else if (v.getId() == R.id.option_noWhippingButton) {
                selectWhippingOption((Button) v, "없이");
            }else if (v.getId() == R.id.option_littleWhippingButton) {
                selectWhippingOption((Button) v, "조금");
            } else if (v.getId() == R.id.option_normalWhippingButton) {
                selectWhippingOption((Button) v, "보통");
            } else if (v.getId() == R.id.option_muchWhippingButton) {
                selectWhippingOption((Button) v, "많이");
            } else if (v.getId() == R.id.option_selectButton) {
                Log.d("옵션 선택 결과 : ", temperature + " & " + syrup + " & " + whipping);
                addItemToAdapter();
                dialog.dismiss();
            } else if (v.getId() == R.id.option_cancelButton) {
                dialog.dismiss();
            }
        }
    };

    // 온도 옵션 버튼 선택 시 호출되는 메서드
    private void selectTemperatureOption(Button optionButton, String option) {
        if (selectedTemperatureButton != null) {
            // 이전에 선택된 버튼이 있다면 색상과 텍스트 색상을 원래대로 변경합니다.
            selectedTemperatureButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.white));
            selectedTemperatureButton.setTextColor(Color.BLACK);
        }

        // 선택된 버튼에 주황색 배경과 흰색 텍스트 색상을 적용합니다.
        optionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFBB33")));
        optionButton.setTextColor(Color.WHITE);

        // 선택된 버튼을 추적합니다.
        selectedTemperatureButton = optionButton;

        // 선택된 온도 옵션을 저장합니다.
        temperature = option;
    }

    // 시럽 옵션 버튼 선택 시 호출되는 메서드
    private void selectSyrupOption(Button optionButton, String option) {
        if (selectedSyrupButton != null) {
            // 이전에 선택된 버튼이 있다면 색상과 텍스트 색상을 원래대로 변경합니다.
            selectedSyrupButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.white));
            selectedSyrupButton.setTextColor(Color.BLACK);
        }

        // 선택된 버튼에 주황색 배경과 흰색 텍스트 색상을 적용합니다.
        optionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFBB33")));
        optionButton.setTextColor(Color.WHITE);

        // 선택된 버튼을 추적합니다.
        selectedSyrupButton = optionButton;

        // 선택된 시럽 옵션을 저장합니다.
        syrup = option;
    }

    private void selectWhippingOption(Button optionButton, String option) {
        if (selectedWhippingButton != null) {
            selectedWhippingButton.setBackgroundTintList(requireContext().getColorStateList(android.R.color.white));
            selectedWhippingButton.setTextColor(Color.BLACK);
        }
        optionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFBB33")));
        optionButton.setTextColor(Color.WHITE);
        selectedWhippingButton = optionButton;
        whipping = option;
    }
}
