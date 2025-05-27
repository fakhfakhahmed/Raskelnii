import 'package:flutter/material.dart';

class TreatDemande extends StatefulWidget {
  final String name;
  final String message;
  final String time;

  const TreatDemande({
    Key? key,
    required this.name,
    required this.message,
    required this.time,
  }) : super(key: key);

  @override
  _TreatDemandeState createState() => _TreatDemandeState();
}

class _TreatDemandeState extends State<TreatDemande> {
  final TextEditingController _controller = TextEditingController();

  void _onNumberPressed(String number) {
    setState(() {
      _controller.text += number; // Append pressed number
    });
  }

  void _confirmDemande() {
    // Show a SnackBar confirming the update
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: const Text('Demande updated successfully!'),
        backgroundColor: Colors.green,
        duration: const Duration(seconds: 2),
      ),
    );

    // Navigate back to "Accepted Demande" page
    Navigator.pop(context, true); // Return a result to the previous screen
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.green,
        title: Text(widget.name),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
      ),
      body: Column(
        children: [
          // Display an image
          Container(
            padding: const EdgeInsets.all(20),
            alignment: Alignment.center,
            child: Image.asset(
              'assets/imagescale.png',
              height: 200,
              fit: BoxFit.contain,
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              children: [
                const SizedBox(height: 20),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    DropdownButton<String>(
                      value: 'kg',
                      items: <String>['kg', 'g', 'lbs'].map((String value) {
                        return DropdownMenuItem<String>(
                          value: value,
                          child: Text(value),
                        );
                      }).toList(),
                      onChanged: (newValue) {},
                    ),
                    const SizedBox(width: 10),
                    SizedBox(
                      width: 90,
                      child: TextField(
                        controller: _controller,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                        ),
                        keyboardType: TextInputType.number,
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 20),
              ],
            ),
          ),
          SizedBox(
            width: 250,
            height: 300,
            child: GridView.builder(
              physics: const NeverScrollableScrollPhysics(),
              itemCount: 12,
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3,
                mainAxisSpacing: 5,
                crossAxisSpacing: 5,
              ),
              padding: const EdgeInsets.all(10),
              itemBuilder: (context, index) {
                if (index == 9) return const SizedBox(); // Empty space
                if (index == 11) {
                  return IconButton(
                    icon: const Icon(Icons.arrow_forward, color: Colors.green),
                    onPressed: _confirmDemande, // Confirm action
                  );
                }
                return ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.green,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(5),
                    ),
                  ),
                  onPressed: () {
                    _onNumberPressed(index == 10 ? '0' : '${index + 1}');
                  },
                  child: Text(
                    index == 10 ? '0' : '${index + 1}',
                    style: const TextStyle(fontSize: 14),
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
