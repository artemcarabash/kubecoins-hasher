// Configdatei für Continuous Integration Server - Jenkins
kubernetesPipeline {
    // Source Git Repository die überwacht nach Push Events wird
    scmUrl = 'https://gitlab.informatik.hs-furtwangen.de/cnc/hasher-carabasa.git'
    // Pfad zum Container der aktualisiert werden sollte
    containerImage = 'docker.informatik.hs-furtwangen.de/cnc-carabasa/hasher'
    // Lokale Pfad zum Manifestdatei
    chartPath = './src/charts/hasher'
    releaseName = 'hasher-carabasa'
    stagingNamespace = 'cnc'
}