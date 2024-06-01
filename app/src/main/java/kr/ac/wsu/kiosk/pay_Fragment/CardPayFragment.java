package kr.ac.wsu.kiosk.pay_Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import kr.ac.wsu.kiosk.PayActivity;
import kr.ac.wsu.kiosk.R;

public class CardPayFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_cardpay, container, false);

        TextView title_tv = fragmentView.findViewById(R.id.cardpay_title_TextView);
        TextView content_tv = fragmentView.findViewById(R.id.cardpay_content_TextView);
        ImageView img_iv = fragmentView.findViewById(R.id.cardpay_ImageView);
        Button finish_Btn = fragmentView.findViewById(R.id.cardpay_finish_Button);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String select = bundle.getString("select");
            if (select.equals("card")) {
                title_tv.setText("카드결제");
                content_tv.setText("카드를 다음과 같이 투입해 주세요.");
                img_iv.setImageResource(R.drawable.cardinsert_img);
            } else {
                title_tv.setText("NFC결제");
                content_tv.setText("단말기에 다음과 같이 NFC태그를 접촉해주세요.");
                img_iv.setImageResource(R.drawable.samsungpay_img);
            }
        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                finish_Btn.setVisibility(View.VISIBLE);
                title_tv.setText("결제완료");
                content_tv.setText("음료 제작이 완료되면 알려드릴게요");
                img_iv.setImageResource(R.drawable.receipt_img);
            }
        }, 3000);

        finish_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_OK); // 결과 설정
                getActivity().finish();
            }
        });

        return fragmentView;
    }
}
