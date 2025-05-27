// ignore_for_file: prefer_const_constructors, prefer_const_literals_to_create_immutables

import 'package:flutter/material.dart';

class PaymentPage extends StatelessWidget {
  const PaymentPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.green,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        title: const Text('Payment'),
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              // Delivery count
              Container(
                alignment: Alignment.center,
                padding: const EdgeInsets.all(16.0),
                decoration: BoxDecoration(
                  color: Colors.green,
                  borderRadius: BorderRadius.circular(10),
                ),
                child: Column(
                  children: const [
                    Text(
                      'Livraison you have been done',
                      style: TextStyle(color: Colors.white, fontSize: 16),
                    ),
                    SizedBox(height: 5),
                    Text(
                      '7',
                      style: TextStyle(
                          color: Colors.white,
                          fontSize: 32,
                          fontWeight: FontWeight.bold),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 20),

              // Card Reward section
              Container(
                padding: const EdgeInsets.all(16.0),
                decoration: BoxDecoration(
                  color: Colors.green,
                  borderRadius: BorderRadius.circular(10),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    const Text(
                      'Your Card Reward',
                      style: TextStyle(color: Colors.white, fontSize: 16),
                    ),
                    const SizedBox(height: 10),
                    Container(
                      padding: const EdgeInsets.all(16.0),
                      decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(10),
                      ),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: const [
                              Text(
                                'BottleCycle',
                                style: TextStyle(
                                  color: Colors.green,
                                  fontSize: 18,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                              Icon(Icons.credit_card, color: Colors.green),
                            ],
                          ),
                          const SizedBox(height: 10),
                          const Text(
                            '8910 2617 2001 2888',
                            style: TextStyle(
                              fontSize: 20,
                              fontWeight: FontWeight.bold,
                              letterSpacing: 1.5,
                            ),
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 10),
                    const Text(
                      'Balance : \$150',
                      style: TextStyle(color: Colors.white, fontSize: 16),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 20),

              // History section
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: const [
                  Text(
                    'History',
                    style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                  Icon(Icons.filter_list, color: Colors.grey),
                ],
              ),
              const SizedBox(height: 10),

              // History entries
              Column(
                children: [
                  buildHistoryItem(
                      date: 'September 20, 2021 12:09:01',
                      transactionId: '65d7dc4b3128c0459f',
                      amount: '+\$0.50'),
                  buildHistoryItem(
                      date: 'September 19, 2021 13:19:01',
                      transactionId: '617e82277d270fb1',
                      amount: '+\$1'),
                  buildHistoryItem(
                      date: 'September 18, 2021 17:20:12',
                      transactionId: '6f6c04e93c8a5bd2',
                      amount: '+\$0.75'),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  // Widget to build a single history entry
  Widget buildHistoryItem({
    required String date,
    required String transactionId,
    required String amount,
  }) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 5),
      elevation: 2,
      child: ListTile(
        contentPadding: const EdgeInsets.all(10),
        title: Text(date, style: const TextStyle(fontSize: 14)),
        subtitle: Text(transactionId,
            style: const TextStyle(fontSize: 12, color: Colors.grey)),
        trailing: Text(amount,
            style: const TextStyle(
                fontSize: 16, fontWeight: FontWeight.bold, color: Colors.green)),
      ),
    );
  }
}
