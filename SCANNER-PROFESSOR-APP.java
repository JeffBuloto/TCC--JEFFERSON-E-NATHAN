BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
BluetoothLeScanner scanner = bluetoothAdapter.getBluetoothLeScanner();

ScanCallback scanCallback = new ScanCallback() {
    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        String deviceName = result.getDevice().getName();
        String deviceAddress = result.getDevice().getAddress();

        System.out.println("Encontrado: " + deviceName + " - " + deviceAddress);
    }
};

scanner.startScan(scanCallback);
