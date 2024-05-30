package kr.ac.wsu.kiosk.main_fragment;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import kr.ac.wsu.kiosk.R;

public class SmothieFragment extends Fragment {

    AlertDialog dialog;
    String menu;

    @SuppressLint("UseRequireInsteadOfGet")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_smothie, container, false);

        // 버튼 객체 초기화
        Button blueberryBtn = fragmentView.findViewById(R.id.smothie_blueberry_Button);
        Button strawberryBtn = fragmentView.findViewById(R.id.smothie_strawberry_Button);
        Button mangoBtn = fragmentView.findViewById(R.id.smothie_mango_Button);
        Button citronBtn = fragmentView.findViewById(R.id.smothie_citron_Button);
        Button yogurtBtn = fragmentView.findViewById(R.id.smothie_plainyogurt_Button);
        Button bbyogurtBtn = fragmentView.findViewById(R.id.smothie_blueberryyougurt_Button);

        // 버튼 클릭 리스너 설정
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.smothie_blueberry_Button) {
                    menu = "blueberry";
                    showCustomDialog();
                } else if (id == R.id.smothie_strawberry_Button) {
                    menu = "strawberry";
                    showCustomDialog();
                } else if (id == R.id.smothie_mango_Button) {
                    menu = "mango";
                    showCustomDialog();
                } else if (id == R.id.smothie_plainyogurt_Button) {
                    menu = "plainyogurt";
                    showCustomDialog();
                } else if (id == R.id.smothie_citron_Button) {
                    menu = "citron";
                    showCustomDialog();
                } else if (id == R.id.smothie_blueberryyougurt_Button) {
                    menu = "blueberryyougurt";
                    showCustomDialog();
                }
            }
        };

        // 모든 버튼에 리스너 설정
        blueberryBtn.setOnClickListener(buttonClickListener);
        strawberryBtn.setOnClickListener(buttonClickListener);
        mangoBtn.setOnClickListener(buttonClickListener);
        yogurtBtn.setOnClickListener(buttonClickListener);
        citronBtn.setOnClickListener(buttonClickListener);
        bbyogurtBtn.setOnClickListener(buttonClickListener);

        return fragmentView;
    }

    private Button selectedTemperatureButton; // 현재 선택된 온도 옵션 버튼을 추적하기 위한 변수
    private Button selectedSyrupButton; // 현재 선택된 시럽 옵션 버튼을 추적하기 위한 변수
    private Button selectedWhippingButton; // 현재 선택된 휘핑 옵션 버튼을 추적하기 위한 변수
    private String temperature, syrup, whipping; // 선택된 온도와 시럽, 휘핑을 저장하는 변수

    private void showCustomDialog() {
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
        Button littleWhippingBtn = dialogView.findViewById(R.id.option_littleWhippingButton);
        Button normalWhippingBtn = dialogView.findViewById(R.id.option_normalWhippingButton);
        Button muchWhippingBtn = dialogView.findViewById(R.id.option_muchWhippingButton);

        // 선택 완료, 취소
        Button doneBtn = dialogView.findViewById(R.id.option_selectButton);
        Button cancelBtn = dialogView.findViewById(R.id.option_cancelButton);

        // 선택 메뉴 출력
        ImageView menuImageView = dialogView.findViewById(R.id.option_image_imageView);
        TextView menuTextView = dialogView.findViewById(R.id.option_name_textView);
        TextView menuPrice_TextView = dialogView.findViewById(R.id.option_price_textView);
        setOption_init(menuImageView, menuTextView, menuPrice_TextView,menu);

        // 기본 선택값으로 ice를 설정합니다.
        selectTemperatureOption(iceBtn, "Ice");
        // 기본 선택값으로 normal을 설정합니다.
        selectSyrupOption(normalSyrupBtn, "Normal");
        // 기본 선택값으로 normal을 설정
        selectWhippingOption(normalWhippingBtn, "Normal");

        // 버튼 클릭 리스너 설정
        hotBtn.setOnClickListener(optionButtonClickListener);
        iceBtn.setOnClickListener(optionButtonClickListener);
        littleSyrupBtn.setOnClickListener(optionButtonClickListener);
        normalSyrupBtn.setOnClickListener(optionButtonClickListener);
        muchSyrupBtn.setOnClickListener(optionButtonClickListener);
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
            case "blueberry":
                menuImageView.setImageResource(R.drawable.blueberry_smothie);
                menuTextView.setText("블루베리 스무디");
                menuPrice_TextView.setText("3000원");
                break;
            case "strawberry":
                menuImageView.setImageResource(R.drawable.strawberry_smothie);
                menuTextView.setText("딸기 스무디");
                menuPrice_TextView.setText("3500원");
                break;
            case "mango":
                menuImageView.setImageResource(R.drawable.mango_smothie);
                menuTextView.setText("망고 스무디");
                menuPrice_TextView.setText("3800원");
                break;
            case "plainyogurt":
                menuImageView.setImageResource(R.drawable.plainyogurt_smothie);
                menuTextView.setText("플레인요거트 스무디");
                menuPrice_TextView.setText("3800원");
                break;
            case "citron":
                menuImageView.setImageResource(R.drawable.citron_smothie);
                menuTextView.setText("유자 스무디");
                menuPrice_TextView.setText("3500원");
                break;
            case "blueberryyougurt":
                menuImageView.setImageResource(R.drawable.blueberryyougurt_smothie);
                menuTextView.setText("블루베리요거트 스무디");
                menuPrice_TextView.setText("4000원");
                break;
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
                selectSyrupOption((Button) v, "Little");
            } else if (v.getId() == R.id.option_normalSyrupButton) {
                selectSyrupOption((Button) v, "Normal");
            } else if (v.getId() == R.id.option_muchSyrupButton) {
                selectSyrupOption((Button) v, "Much");
            } else if (v.getId() == R.id.option_littleWhippingButton) {
                selectWhippingOption((Button) v, "Little");
            } else if (v.getId() == R.id.option_normalWhippingButton) {
                selectWhippingOption((Button) v, "Normal");
            } else if (v.getId() == R.id.option_muchWhippingButton) {
                selectWhippingOption((Button) v, "Much");
            } else if (v.getId() == R.id.option_selectButton) {
                Log.d("옵션 선택 결과 : ", temperature + " & " + syrup + " & " + whipping);
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
