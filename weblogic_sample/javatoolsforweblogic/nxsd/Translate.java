// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 29/10/2011 18:45:53
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Translate.java

package xlator.util;

import java.io.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import oracle.tip.pc.services.translation.framework.*;
import oracle.tip.pc.services.translation.xlators.dtd.DTDTranslator;
import oracle.tip.pc.services.translation.xlators.nxsd.ErrorList;
import oracle.tip.pc.services.translation.xlators.xsd.XSDTranslator;
import oracle.xml.parser.schema.*;
import oracle.xml.parser.v2.*;
import oracle.xml.schemavalidator.XSDValidator;
import org.w3c.dom.*;

public class Translate
{

    public Translate()
    {
    }

    public static void main(String args[])
    {
        System.out.println("");
        try
        {
            if(args.length == 0 || args[0].equals("-help"))
            {
                showUsage();
                System.exit(1);
            }
            for(int i = 0; i < args.length; i++)
                if(args[i].startsWith("-"))
                {
                    if(args[i].equals("-schema"))
                    {
                        if(++i == args.length)
                        {
                            System.out.println("[Error] :: Missing argument to -schema option.");
                            System.exit(1);
                        }
                        fSchemaLocation = args[i];
                        continue;
                    }
                    if(args[i].equals("-root"))
                    {
                        if(++i == args.length)
                        {
                            System.out.println("[Error] :: Missing argument to -root option.");
                            System.exit(1);
                        }
                        fRootElementName = args[i];
                        continue;
                    }
                    if(args[i].equals("-output"))
                    {
                        if(++i == args.length)
                        {
                            System.out.println("[Error] :: Missing argument to -output option.");
                            System.exit(1);
                        }
                        fOutput = args[i];
                        continue;
                    }
                    if(args[i].equals("-debatch"))
                    {
                        if(++i == args.length)
                        {
                            System.out.println("[Error] :: Missing argument to -debatch option.");
                            System.exit(1);
                        }
                        try
                        {
                            fPublishSize = Integer.parseInt(args[i]);
                            continue;
                        }
                        catch(Exception exception1)
                        {
                            System.out.println((new StringBuilder()).append("[Error] :: Invalid value {").append(args[i]).append("} for -debatch option.").toString());
                        }
                        System.exit(1);
                        continue;
                    }
                    if(args[i].equals("-inbound"))
                    {
                        fInbound = true;
                        continue;
                    }
                    if(args[i].equals("-buffer"))
                    {
                        fBuffer = true;
                        continue;
                    }
                    if(args[i].equals("-outbound"))
                    {
                        fOutbound = true;
                        continue;
                    }
                    if(args[i].equals("-validate"))
                    {
                        if(++i == args.length)
                        {
                            System.out.println("[Error] :: Missing argument to -validate option.");
                            System.exit(1);
                        }
                        if(args[i].equals("in"))
                        {
                            fValidateInput = true;
                            continue;
                        }
                        if(args[i].equals("out"))
                        {
                            fValidateOutput = true;
                            continue;
                        }
                        if(args[i].equals("both"))
                        {
                            fValidateInput = true;
                            fValidateOutput = true;
                        }
                        continue;
                    }
                    if(!args[i].equals("-debug"))
                        continue;
                    if(++i == args.length)
                    {
                        System.out.println("[Error] :: Missing argument to -debug option.");
                        System.exit(1);
                    }
                    if(args[i].equals("on"))
                    {
                        fDebug = true;
                        continue;
                    }
                    if(args[i].equals("off"))
                        fDebug = false;
                } else
                {
                    fInput = args[i];
                }

            if(fInput == null)
            {
                System.out.println("[Error] :: No input file specified.");
                System.exit(1);
            }
            if(fOutput == null)
            {
                System.out.println("[Error] :: No output file specified.");
                System.exit(1);
            }
            if(fInbound && fOutbound)
            {
                System.out.println("[Error] :: Both -inbound and -outbound option cannot be specified together.");
                System.exit(1);
            }
            if(!fInbound && !fOutbound)
            {
                System.out.println("[Error] :: Must specify either -inbound or -outbound option.");
                System.exit(1);
            }
            if(fSchemaLocation == null)
            {
                System.out.println("[Error] :: No NativeSchema specified.");
                System.exit(1);
            }
            if(fRootElementName == null)
            {
                System.out.println("[Error] :: No root element declaration specified.");
                System.exit(1);
            }
            debug("Using the following input parameters...");
            debug((new StringBuilder()).append("  Input file   = ").append(fInput).toString());
            debug((new StringBuilder()).append("  Output file  = ").append(fOutput).toString());
            debug((new StringBuilder()).append("  Schema file  = ").append(fSchemaLocation).toString());
            debug((new StringBuilder()).append("  Root element = ").append(fRootElementName).toString());
            if(fPublishSize > 0)
            {
                debug("  De-batching  = true");
                debug((new StringBuilder()).append("  Publish size = ").append(fPublishSize).toString());
            } else
            {
                debug("  De-batching  = false");
            }
            if(fInbound)
                debug("  Translation  = inbound");
            else
                debug("  Translation  = outbound");
            debug((new StringBuilder()).append("  Validate In  = ").append(fValidateInput ? "true" : "false").toString());
            debug((new StringBuilder()).append("  Validate Out = ").append(fValidateOutput ? "true" : "false").toString());
            Translator translator = createXlator();
            if(fInbound)
                doInboundTranslation(translator);
            else
                doOutboundTranslation(translator);
        }
        catch(Exception exception)
        {
            System.out.println((new StringBuilder()).append("[Error] :: ").append(exception.getMessage()).toString());
            exception.printStackTrace();
        }
    }

    private static void showUsage()
    {
        System.out.println("usage: java xlator.util.Translate (options) <Input File>");
        System.out.println();
        System.out.println("options:");
        System.out.println("  -schema <Native Schema>       The schema to use for translation.");
        System.out.println("  -root <name>                  Localname of the root element declaration to use.");
        System.out.println("  -inbound  | -outbound         Do inbound/outbound translation.");
        System.out.println("  -output <Output File>         The translated file.");
        System.out.println("  -debatch <Publish Size>       Turn debatching on with the specified publish size.");
        System.out.println("  -validate {in | out | both}   Turn on xml validation of the input/output/both file.");
        System.out.println("  -debug {on | off}             Turn on/off debug statements.");
        System.out.println("  -help                         This help screen.");
        System.out.println();
        System.out.println("defaults:");
        System.out.println("  Validation:  off");
        System.out.println("  De-batching: off");
        System.out.println("  Debug: on");
    }

    private static Translator createXlator()
        throws Exception
    {
        Object obj = null;
        debug("");
        debug("Loading schema...");
        XSDBuilder xsdbuilder = new XSDBuilder();
        fSchema = xsdbuilder.build((new File(fSchemaLocation)).toURL());
        debug("Done.");
        debug("Finding root element...");
        String s = fSchema.getSchemaTargetNS();
        XSDElement xsdelement = fSchema.getElement(s, fRootElementName);
        if(xsdelement == null)
        {
            throw new Exception((new StringBuilder()).append("Unable to find root by the name ").append(fRootElementName).append(" in the given schema.").toString());
        } else
        {
            debug("Done.");
            debug("Creating Translator...");
            TranslatorFactory translatorfactory = TranslatorFactory.getInstance();
            Translator translator = translatorfactory.createTranslator(fSchema, xsdelement);
            debug("Done.");
            return translator;
        }
    }

    private static void doInboundTranslation(Translator translator)
        throws Exception
    {
        FileInputStream fileinputstream = new FileInputStream(fInput);
        DOMResult domresult = new DOMResult();
        if(fPublishSize > 0)
        {
            TranslationContext translationcontext = new TranslationContext();
            translationcontext.setFeature("http://xmlns.oracle.com/pcbpel/nxsd/features/debatching", true);
            translationcontext.setFeature("http://xmlns.oracle.com/pcbpel/nxsd/properties/end-of-file", false);
            translationcontext.setProperty("http://xmlns.oracle.com/pcbpel/nxsd/properties/publishsize", (new StringBuilder()).append("").append(fPublishSize).toString());
            debug((new StringBuilder()).append("Debatching with publish size as ").append(fPublishSize).append("...").toString());
            int i = 1;
            boolean flag = false;
            while(!translationcontext.getFeature("http://xmlns.oracle.com/pcbpel/nxsd/properties/end-of-file")) 
            {
                debug("");
                debug((new StringBuilder()).append("Translating Batch ").append(i).append("...").toString());
                long l2 = getCurrentTime();
                translator.translateFromNative(fileinputstream, domresult, translationcontext);
                long l3 = getCurrentTime();
                double d1 = (double)(l3 - l2) / 1000D;
                Node node = domresult.getNode();
                System.out.println((new StringBuilder()).append("**** NODE**********>").append(node.getNodeName()).toString());
                if(node != null)
                {
                    NodeList nodelist = node.getChildNodes();
                    System.out.println((new StringBuilder()).append("***** LENGTH [").append(nodelist.getLength()).append("]").toString());
                    for(int k = 0; k < nodelist.getLength(); k++)
                    {
                        Node node1 = nodelist.item(k);
                    }

                }
                XMLElement xmlelement1 = (XMLElement)domresult.getNode();
                FileOutputStream fileoutputstream1 = new FileOutputStream((new StringBuilder()).append(fOutput).append("_batch_").append(i).append(".xml").toString());
                xmlelement1.print(fileoutputstream1);
                fileoutputstream1.flush();
                fileoutputstream1.close();
                debug((new StringBuilder()).append("  Time taken for translation = ").append(d1).append(" seconds.").toString());
                debug((new StringBuilder()).append("  EOF    = ").append(translationcontext.getFeature("http://xmlns.oracle.com/pcbpel/nxsd/properties/end-of-file")).toString());
                debug((new StringBuilder()).append("  LINE   = ").append(translationcontext.getProperty("http://xmlns.oracle.com/pcbpel/nxsd/properties/line-number")).toString());
                debug((new StringBuilder()).append("  COLUMN = ").append(translationcontext.getProperty("http://xmlns.oracle.com/pcbpel/nxsd/properties/column-number")).toString());
                debug((new StringBuilder()).append("  OFFSET = ").append(translationcontext.getProperty("http://xmlns.oracle.com/pcbpel/nxsd/properties/offset")).toString());
                ErrorList errorlist = (ErrorList)translationcontext.getProperty("http://xmlns.oracle.com/pcbpel/nxsd/properties/error-list");
                if(errorlist != null && errorlist.getErrorCount() > 0)
                {
                    for(int i1 = 0; i1 < errorlist.getErrorCount(); i1++)
                    {
                        debug((new StringBuilder()).append("  Error").append(i1).append(" = [").append(errorlist.getStartLineNumber(i1)).append(",").append(errorlist.getStartColumnNumber(i1)).append("] to [").append(errorlist.getEndLineNumber(i1)).append(",").append(errorlist.getEndColumnNumber(i1)).append("]").toString());
                        Exception exception1 = errorlist.getException(i1);
                        exception1.printStackTrace();
                    }

                }
                debug((new StringBuilder()).append("Batch ").append(i).append(" done.").toString());
                i++;
            }
            debug("");
            debug("Debatching Done.");
            if(fValidateInput)
                if((translator instanceof DTDTranslator) || (translator instanceof XSDTranslator))
                {
                    debug("");
                    debug("Validating input xml...");
                    validate(fInput, fSchema);
                } else
                {
                    debug("");
                    debug("Can't validate input data.");
                }
            if(fValidateOutput)
            {
                debug("");
                for(int j = 1; j < i; j++)
                {
                    debug((new StringBuilder()).append("Validating output xml of batch[").append(j).append("]...").toString());
                    validate((new StringBuilder()).append(fOutput).append("_batch_").append(j).append(".xml").toString(), fSchema);
                }

            }
        } else
        {
            debug("Translating inbound...");
            long l = getCurrentTime();
            try
            {
                translator.translateFromNative(fileinputstream, domresult, null);
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
                throw exception;
            }
            long l1 = getCurrentTime();
            debug("Done.");
            double d = (double)(l1 - l) / 1000D;
            XMLElement xmlelement = (XMLElement)domresult.getNode();
            FileOutputStream fileoutputstream = new FileOutputStream(fOutput);
            xmlelement.print(fileoutputstream);
            fileoutputstream.flush();
            fileoutputstream.close();
            debug((new StringBuilder()).append("Time taken for translation = ").append(d).append(" seconds.").toString());
            if(fValidateInput)
                if((translator instanceof DTDTranslator) || (translator instanceof XSDTranslator))
                {
                    debug("");
                    debug("Validating input xml...");
                    validate(fInput, fSchema);
                } else
                {
                    debug("");
                    debug("Can't validate input data.");
                }
            if(fValidateOutput)
            {
                debug("");
                debug("Validating output xml...");
                validate(fOutput, fSchema);
            }
        }
    }

    private static void doOutboundTranslation(Translator translator)
        throws Exception
    {
        debug("Creating DOMSource...");
        DOMParser domparser = new DOMParser();
        domparser.parse(new FileInputStream(fInput));
        oracle.xml.parser.v2.XMLDocument xmldocument = domparser.getDocument();
        DOMSource domsource = new DOMSource(xmldocument);
        debug("Creating output stream");
        Object obj = null;
        if(fBuffer)
            obj = new BufferedOutputStream(new FileOutputStream(fOutput));
        else
            obj = new FileOutputStream(fOutput);
        TranslationContext translationcontext = new TranslationContext();
        debug("Translating outbound...");
        long l = getCurrentTime();
        translator.translateToNative(domsource, ((OutputStream) (obj)), translationcontext);
        long l1 = getCurrentTime();
        debug("Done.");
        double d = (double)(l1 - l) / 1000D;
        ((OutputStream) (obj)).flush();
        ((OutputStream) (obj)).close();
        debug((new StringBuilder()).append("Time taken for translation = ").append(d).append(" seconds.").toString());
        if(fValidateInput)
        {
            debug("");
            debug("Validating input xml...");
            validate(fInput, fSchema);
        }
        if(fValidateOutput)
            if((translator instanceof DTDTranslator) || (translator instanceof XSDTranslator))
            {
                debug("");
                debug("Validating output xml...");
                validate(fOutput, fSchema);
            } else
            {
                debug("");
                debug("Can't validate output data.");
            }
    }

    private static void validate(String s, XMLSchema xmlschema)
        throws Exception
    {
        XMLError xmlerror = new XMLError();
        XSDValidator xsdvalidator = new XSDValidator();
        xsdvalidator.setSchema(xmlschema);
        xsdvalidator.setError(xmlerror);
        FileInputStream fileinputstream = new FileInputStream(s);
        xsdvalidator.validate(fileinputstream);
        fileinputstream.close();
        if(xmlerror.getNumMessages() > 0)
        {
            xmlerror.flushErrors();
            debug("Validation Failed.");
        }
        debug("File is valid.");
    }

    private static long getCurrentTime()
    {
        return System.currentTimeMillis();
    }

    private static void debug(String s)
    {
        if(fDebug)
            System.out.println((new StringBuilder()).append("DEBUG :: ").append(s).toString());
    }

    private static String fInput;
    private static String fOutput;
    private static String fSchemaLocation;
    private static String fRootElementName;
    private static XMLSchema fSchema;
    private static XSDElement fRoot;
    private static boolean fInbound;
    private static boolean fOutbound;
    private static boolean fValidateInput;
    private static boolean fValidateOutput;
    private static boolean fDebug = true;
    private static int fPublishSize;
    private static boolean fBuffer;

}
