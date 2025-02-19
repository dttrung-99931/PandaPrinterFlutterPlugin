package com.print_plugin.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.print_plugin.R;

import java.util.ArrayList;

/**
 * Created by charlie on 2017/4/2.
 * Bluetooth search state braodcastrecever
 */

public class DeviceReceiver extends BroadcastReceiver {


    private ArrayList<String> deviceList_found=new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView listView;

    public DeviceReceiver(ArrayList<String> deviceList_found, ArrayAdapter<String> adapter, ListView listView) {
        this.deviceList_found = deviceList_found;
        this.adapter = adapter;
        this.listView = listView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)){
            //search for new device
            BluetoothDevice btd=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            //the device din't boned
            if(btd.getBondState()!= BluetoothDevice.BOND_BONDED){
                if (!deviceList_found.contains(btd.getName()+'\n'+btd.getAddress())){
                    deviceList_found.add(btd.getName()+'\n'+btd.getAddress());
                    try{
                         adapter.notifyDataSetChanged();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
            //search result
            if(listView.getCount()==0){
                deviceList_found.add("no devices are found"/*context.getString(R.string.none_ble_device)*/);
                try{
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        }

    }
}
