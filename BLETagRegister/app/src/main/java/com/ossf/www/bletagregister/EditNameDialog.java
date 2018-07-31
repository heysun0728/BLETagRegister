package com.ossf.www.bletagregister;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import static com.ossf.www.bletagregister.HomeActivity.regDevice_list;

public class EditNameDialog extends Dialog{
    private TextView tv_mac;
    private Button btn_confirm;
    private EditText et_name;
    String mac;
    BLEdevice device;
    public EditNameDialog(@NonNull Context context,BLEdevice d) {
        super(context,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        setContentView(R.layout.dialog_edit_name);
        //device = d
        if(d==null){
            Log.v("apple","d is null");
        }else{
            Log.v("apple","d is not null");
        }
        device=new BLEdevice(d);
        mac=device.getMac();
        //initiallize
        this.setTitle("為此設備命名");
        et_name=(EditText)findViewById(R.id.et_name);
        tv_mac=(TextView)findViewById(R.id.tv_mac);
        tv_mac.setText("MAC: "+mac);
        btn_confirm=(Button)findViewById(R.id.btn_confirm_name);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm(mac);
            }
        });
    }
    public void confirm(String mac){
        String name=et_name.getText().toString();
        String data = mac+" "+name+"\n";
        Log.v("apple","data="+data);
        //寫進ble.txt檔案裡
        try {
            FileStream fs=new FileStream();
            fs.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //放進regDevice_list
        device.isReg=true;
        device.register(name);
        regDevice_list.put(device.getMac(),device);
        this.dismiss();
    }
}