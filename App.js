import React from 'react';
import {View, Text, NativeModules} from 'react-native';

const App = () => {
  const {DetectCallsModule} = NativeModules;
  console.log(DetectCallsModule);
  DetectCallsModule.startListening();
  DetectCallsModule.testModule('fatma', () => {
    console.log('Testing module');
  });

  return (
    <View>
      <Text>TRUECALLER APP</Text>
    </View>
  );
};

export default App;
