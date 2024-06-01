package kr.ac.wsu.kiosk.pay_Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import kr.ac.wsu.kiosk.R;
public class SelectFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_select, container, false);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Button cardBtn = (Button) fragmentView.findViewById(R.id.pay_card_Button);
        Button nfcBtn = (Button) fragmentView.findViewById(R.id.pay_nfc_Button);

        Bundle bundle = new Bundle();
        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("select", "card");

                CardPayFragment cardPayFragment = new CardPayFragment();
                cardPayFragment.setArguments(bundle);
                transaction.replace(R.id.pay_Framelayout, cardPayFragment);
                transaction.commit();
            }
        });

        nfcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("select", "nfc");

                CardPayFragment cardPayFragment = new CardPayFragment();
                cardPayFragment.setArguments(bundle);
                transaction.replace(R.id.pay_Framelayout, cardPayFragment);
                transaction.commit();
            }
        });

        return fragmentView;
    }
}