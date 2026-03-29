BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
BluetoothLeAdvertiser advertiser = bluetoothAdapter.getBluetoothLeAdvertiser();

AdvertiseSettings settings = new AdvertiseSettings.Builder()
        .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
        .setConnectable(false)
        .build();

ParcelUuid pUuid = new ParcelUuid(UUID.fromString("0000180D-0000-1000-8000-00805F9B34FB"));

AdvertiseData data = new AdvertiseData.Builder()
        .addServiceUuid(pUuid)
        .setIncludeDeviceName(true)
        .build();

AdvertiseCallback callback = new AdvertiseCallback() {
    @Override
    public void onStartSuccess(AdvertiseSettings settingsInEffect) {
        System.out.println("Transmitindo...");
        @Override
    public void onStartFailure(int errorCode) {
        System.out.println("Erro ao transmitir: " + errorCode);
    }
};

advertiser.startAdvertising(settings, data, callback);
    }

    @Override
    public void onStartFailure(int errorCode) {
        System.out.println("Erro ao transmitir: " + errorCode);
    }
};

advertiser.startAdvertising(settings, data, callback);
