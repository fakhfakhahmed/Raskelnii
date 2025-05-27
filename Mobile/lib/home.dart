import 'demande.dart'; // Import the Suivi Demande page
import 'payment.dart'; // Import the Payment page
import 'package:flutter/material.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.green,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.white),
          onPressed: () {
            Navigator.pop(context); // Go back to the previous page
          },
        ),
        title: Image.asset(
          'assets/NormalWhite.png', // Replace with your logo asset path
          height: 50,
        ),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const SizedBox(height: 20),
            const Center(
              child: Text(
                "Where do you want to recycle?",
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                  color: Colors.black,
                ),
              ),
            ),
            const SizedBox(height: 20),
            Expanded(
              child: GridView.count(
                crossAxisCount: 2,
                crossAxisSpacing: 20,
                mainAxisSpacing: 20,
                children: [
                  // Home Button
                  _buildGridButton(
                    icon: Icons.home,
                    label: "Home",
                    onTap: () {
                      // This is the Home Page itself, no action needed
                    },
                  ),
                  // Suivi Demande Button
                  _buildGridButton(
                    icon: Icons.recycling,
                    label: "Suivi Demande",
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => const DemandePage(),
                        ),
                      );
                    },
                  ),
                  // Accepted Demande Button
                  _buildGridButton(
                    icon: Icons.check_circle_outline,
                    label: "Accepted Demande",
                    onTap: () {
                      // Navigate to an Accepted Demande page (to be implemented)
                      // Navigator.push(
                      //   context,
                      //   MaterialPageRoute(builder: (context) => const AcceptedDemandePage()),
                      // );
                    },
                  ),
                  // Payment Button
                  _buildGridButton(
                    icon: Icons.attach_money,
                    label: "Payment",
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => const PaymentPage(),
                        ),
                      );
                    },
                  ),
                ],
              ),
            ),
            const SizedBox(height: 20),
            Center(
              child: ElevatedButton(
                onPressed: () {
                  // Add action for "Cycle it" button
                  // e.g., show a dialog or navigate to a specific page
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green,
                  padding: const EdgeInsets.symmetric(horizontal: 80, vertical: 15),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10),
                  ),
                ),
                child: const Text(
                  "Cycle it",
                  style: TextStyle(
                    fontSize: 18,
                    color: Colors.white,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  // Helper widget for grid buttons
  Widget _buildGridButton({
    required IconData icon,
    required String label,
    required VoidCallback onTap,
  }) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(10),
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.1),
              blurRadius: 10,
              offset: const Offset(0, 5),
            ),
          ],
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(icon, size: 40, color: Colors.green),
            const SizedBox(height: 10),
            Text(
              label,
              style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
              textAlign: TextAlign.center,
            ),
          ],
        ),
      ),
    );
  }
}
