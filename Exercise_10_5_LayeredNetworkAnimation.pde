// The Nature of Code
// Daniel Shiffman
// http://natureofcode.com

// An animated drawing of a Neural Network

Network network;

void setup() {
  size(1000,800); 
  // Create the Network object
  network = new Network(width/2, height/2);

  int layers = 2;
  int inputs = 5;
  
   Neuron output1 = new Neuron(250, 220);
   Neuron output2 = new Neuron(250, 180);
   Neuron output3 = new Neuron(250, 140);
   Neuron output4 = new Neuron(250, 100);
   Neuron output5 = new Neuron(250, 60);
   Neuron output6 = new Neuron(250, 20);
   Neuron output7 = new Neuron(250, -20);
   Neuron output8 = new Neuron(250, -60);
   Neuron output9 = new Neuron(250, -100);
   Neuron output10 = new Neuron(250, -140);
   Neuron output11 = new Neuron(250, -180);
   Neuron output12 = new Neuron(250, -220);
   
  for (int i = 0; i < layers; i++) {
    for (int j = 0; j < inputs; j++) {
      float x = map(i, 0, layers, -250, 300);
      float y = map(j, 0, inputs-1, -75, 75);
      Neuron n = new Neuron(x, y);
      if (i > 0) {
        for (int k = 0; k < inputs - 1; k++) {
          Neuron prev = network.neurons.get(network.neurons.size()-inputs+k-j); 
          network.connect(prev, n, random(1));
        }
      }
      if (i == layers-1) {
        network.connect(n, output1, random(1));
        network.connect(n, output2, random(1));
        network.connect(n, output3, random(1));
        network.connect(n, output4, random(1));
        network.connect(n, output5, random(1));
        network.connect(n, output6, random(1));
        network.connect(n, output7, random(1));
        network.connect(n, output8, random(1));
        network.connect(n, output9, random(1));
        network.connect(n, output10, random(1));
        network.connect(n, output11, random(1));
        network.connect(n, output12, random(1));
      }
      network.addNeuron(n);
    }
  } 
  network.addNeuron(output1);
  network.addNeuron(output2);
  network.addNeuron(output3);
  network.addNeuron(output4);
  network.addNeuron(output5);
  network.addNeuron(output6);
  network.addNeuron(output7);
  network.addNeuron(output8);
  network.addNeuron(output9);
  network.addNeuron(output10);
  network.addNeuron(output11);
  network.addNeuron(output12);
}

void draw() {
  background(255);
  // Update and display the Network
  network.update();
  network.display();

  // Every 30 frames feed in an input
  if (frameCount % 30 == 0) {
    network.feedforward(random(1),random(1));
  }
}