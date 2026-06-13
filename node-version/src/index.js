import DiagnosticService from './service/DiagnosticService.js';

console.log('====================================');
console.log('POS Network & Database Diagnostic Tool');
console.log('====================================');

try {

    const diagnosticService =
        new DiagnosticService();

    await diagnosticService.startDiagnostic();

} catch (error) {

    console.error(
        `Application failed: ${error.message}`
    );

    console.error(error);

}

console.log(
    'Diagnostic process completed.'
);
