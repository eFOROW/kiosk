package kr.ac.wsu.kiosk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import kr.ac.wsu.kiosk.pay_Fragment.CardPayFragment;
import kr.ac.wsu.kiosk.pay_Fragment.SelectFragment;

public class PayActivity extends FragmentActivity {

    private SelectFragment selectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pay);

        List<Data> data = DataManager.getInstance().getDataList();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        selectFragment = new SelectFragment();
        fragmentTransaction.add(R.id.pay_Framelayout, selectFragment);
        fragmentTransaction.commit();
    }
}