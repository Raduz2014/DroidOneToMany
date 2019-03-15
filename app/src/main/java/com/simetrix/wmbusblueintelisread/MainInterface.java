package com.simetrix.wmbusblueintelisread;

import com.simetrix.wmbusblueintelisread.InterFragmentCommunication.BlueDeviceInterface;
import com.simetrix.wmbusblueintelisread.InterFragmentCommunication.DefaultInterface;
import com.simetrix.wmbusblueintelisread.InterFragmentCommunication.ReadingMeterInterface;
import com.simetrix.wmbusblueintelisread.InterFragmentCommunication.StartMenuInterface;
import com.simetrix.wmbusblueintelisread.base.HostActivityInterface;

public interface MainInterface
        extends
            HostActivityInterface,
            DefaultFragment.OnDefaultFragmentInteractionListener,
            BlueDevicePaireFragment.OnBluethDevicePairFragmentInteractionListener,
            DefaultInterface,
            BlueDeviceInterface,
            StartMenuInterface,
            ReadingMeterInterface

{

}