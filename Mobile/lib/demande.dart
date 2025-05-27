import 'package:flutter/material.dart';
import 'treatdemande.dart'; // Import the TreatDemande screen

class DemandePage extends StatefulWidget {
  const DemandePage({Key? key}) : super(key: key);

  @override
  _DemandePageState createState() => _DemandePageState();
}

class _DemandePageState extends State<DemandePage> {
  // Sample data for Demande
  List<Map<String, dynamic>> demandeList = [
    {
      'name': 'Fakhfakh Ahmed',
      'message':
          'Your account has been activated. Now you can explore and join our program.',
      'status': 'pending',
      'time': '2 days ago',
    },
    {
      'name': 'John',
      'message': '38 Rue Houdaybiya',
      'status': 'available',
      'time': 'Just now',
    },
  ];

  // Sample data for Accepted Demandes
  List<Map<String, dynamic>> acceptedList = [
    {
      'name': 'John',
      'message': '38 Rue Houdaybiya',
      'time': 'Just now',
      'status': 'treated',
    },
    {
      'name': 'Ahmed',
      'message': 'Jumpark filej sehli',
      'time': '7 days ago',
      'status': 'treated',
    },
  ];

  String selectedTab = 'Demande';

  // Accept a Demande
  void acceptDemande(int index) {
    setState(() {
      // Add to Accepted List with 'new' status
      acceptedList.add({
        'name': demandeList[index]['name'],
        'message': demandeList[index]['message'],
        'time': demandeList[index]['time'],
        'status': 'new', // Mark as new
      });

      // Update status of Demande to 'accepted'
      demandeList[index]['status'] = 'accepted';
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.green,
        title: const Text('Suivi Demande'),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
      ),
      body: Column(
        children: [
          // Tab Selector
          Container(
            padding: const EdgeInsets.all(10.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                // Demande Tab
                GestureDetector(
                  onTap: () {
                    setState(() {
                      selectedTab = 'Demande';
                    });
                  },
                  child: Container(
                    padding: const EdgeInsets.symmetric(
                        vertical: 10, horizontal: 20),
                    decoration: BoxDecoration(
                      color: selectedTab == 'Demande'
                          ? Colors.green
                          : Colors.grey[200],
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Text(
                      'Demande',
                      style: TextStyle(
                        color: selectedTab == 'Demande'
                            ? Colors.white
                            : Colors.black,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ),
                const SizedBox(width: 10),
                // Accepted Tab
                GestureDetector(
                  onTap: () {
                    setState(() {
                      selectedTab = 'Accepted';
                    });
                  },
                  child: Container(
                    padding: const EdgeInsets.symmetric(
                        vertical: 10, horizontal: 20),
                    decoration: BoxDecoration(
                      color: selectedTab == 'Accepted'
                          ? Colors.green
                          : Colors.grey[200],
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Text(
                      'Accepted',
                      style: TextStyle(
                        color: selectedTab == 'Accepted'
                            ? Colors.white
                            : Colors.black,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
          // Tab Content
          Expanded(
            child: selectedTab == 'Demande'
                ? ListView.builder(
                    itemCount: demandeList.length,
                    itemBuilder: (context, index) {
                      final demande = demandeList[index];
                      return Card(
                        margin: const EdgeInsets.symmetric(
                            vertical: 5, horizontal: 10),
                        child: ListTile(
                          leading: Icon(
                            demande['status'] == 'accepted'
                                ? Icons.check_circle
                                : Icons.circle,
                            color: demande['status'] == 'accepted'
                                ? Colors.green
                                : Colors.red,
                          ),
                          title: Text(demande['name']),
                          subtitle: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(demande['message']),
                              Text(demande['time'],
                                  style: const TextStyle(fontSize: 12)),
                            ],
                          ),
                          trailing: demande['status'] == 'available'
                              ? IconButton(
                                  icon: const Icon(Icons.add_circle,
                                      color: Colors.green),
                                  onPressed: () {
                                    acceptDemande(index);
                                  },
                                )
                              : null,
                        ),
                      );
                    },
                  )
                : ListView.builder(
                    itemCount: acceptedList.length,
                    itemBuilder: (context, index) {
                      final accepted = acceptedList[index];
                      final isNew = accepted['status'] == 'new';

                      return Card(
                        margin: const EdgeInsets.symmetric(
                            vertical: 5, horizontal: 10),
                        child: ListTile(
                          leading: Icon(
                            isNew ? Icons.error : Icons.check_circle,
                            color: isNew ? Colors.red : Colors.green,
                          ),
                          title: Text(accepted['name']),
                          subtitle: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(accepted['message']),
                              Text(accepted['time'],
                                  style: const TextStyle(fontSize: 12)),
                            ],
                          ),
                          trailing: isNew
                              ? ElevatedButton(
                                  onPressed: () async {
                                    // Navigate to TreatDemande Screen
                                    final result = await Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                        builder: (context) => TreatDemande(
                                          name: accepted['name'],
                                          message: accepted['message'],
                                          time: accepted['time'],
                                        ),
                                      ),
                                    );

                                    // Update status if treated
                                    if (result == true) {
                                      setState(() {
                                        acceptedList[index]['status'] =
                                            'treated';
                                      });
                                    }
                                  },
                                  style: ElevatedButton.styleFrom(
                                    backgroundColor: Colors.green,
                                  ),
                                  child: const Text(
                                    'Treat',
                                    style: TextStyle(color: Colors.white),
                                  ),
                                )
                              : null,
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
