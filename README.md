# Rise - GENT2 - Android

## Team Members

- Bram Rampelberg - <bram.rampelberg@student.hogent.be> - BramRampelberg
- Xan Pinson - <xan.pinson@student.hogent.be> - Snowyxa
- Pushwant Sagoo - <pushwant.sagoo@student.hogent.be> - PushwantSagoo
- Sujan Sapkota - <sujan.sapkota@student.hogent.be> - sujansapkota2
- Simon De Roeve - <simon.deroeve@student.hogent.be> - SimonDeRoeve
- Bas Stokmans - <bas.stokmans@student.hogent.be> - baziniser
- Bindo Thorpe - <bindo.thorpe@student.hogent.be> - bindothorpe

## Running the application

Sync gradle files and run MainActivity on an android emulator.

## Testing

In project root run the following commands:

- Run unit tests: `./gradlew test`
- Run android tests:
    - Automatically launch emulator and run tests: `./gradlew connectedAndroidTest`
    - Run tests on already connected emulator (e.g. for github actions): `./gradlew connectedCheck`