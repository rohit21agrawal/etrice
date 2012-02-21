package org.eclipse.etrice.generator.c.gen;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eclipse.emf.common.util.EList;
import org.eclipse.etrice.core.room.ActorClass;
import org.eclipse.etrice.core.room.DetailCode;
import org.eclipse.etrice.core.room.Port;
import org.eclipse.etrice.core.room.ProtocolClass;
import org.eclipse.etrice.core.room.SubSystemClass;
import org.eclipse.etrice.generator.base.ILogger;
import org.eclipse.etrice.generator.c.gen.CExtensions;
import org.eclipse.etrice.generator.etricegen.ActorInstance;
import org.eclipse.etrice.generator.etricegen.ExpandedActorClass;
import org.eclipse.etrice.generator.etricegen.InterfaceItemInstance;
import org.eclipse.etrice.generator.etricegen.PortInstance;
import org.eclipse.etrice.generator.etricegen.Root;
import org.eclipse.etrice.generator.etricegen.SubSystemInstance;
import org.eclipse.etrice.generator.extensions.RoomExtensions;
import org.eclipse.etrice.generator.generic.ProcedureHelpers;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.xbase.lib.IntegerExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
@Singleton
public class SubSystemClassGen {
  @Inject
  private JavaIoFileSystemAccess fileAccess;
  
  @Inject
  private CExtensions stdExt;
  
  @Inject
  private RoomExtensions roomExt;
  
  @Inject
  private ProcedureHelpers helpers;
  
  @Inject
  private ILogger logger;
  
  public void doGenerate(final Root root) {
    EList<SubSystemInstance> _subSystemInstances = root.getSubSystemInstances();
    for (final SubSystemInstance ssi : _subSystemInstances) {
      {
        SubSystemClass _subSystemClass = ssi.getSubSystemClass();
        String _generationTargetPath = this.roomExt.getGenerationTargetPath(_subSystemClass);
        SubSystemClass _subSystemClass_1 = ssi.getSubSystemClass();
        String _path = this.roomExt.getPath(_subSystemClass_1);
        String _operator_plus = StringExtensions.operator_plus(_generationTargetPath, _path);
        String path = _operator_plus;
        SubSystemClass _subSystemClass_2 = ssi.getSubSystemClass();
        String _cHeaderFileName = this.stdExt.getCHeaderFileName(_subSystemClass_2);
        String file = _cHeaderFileName;
        String _operator_plus_1 = StringExtensions.operator_plus("generating SubSystemClass declaration: \'", file);
        String _operator_plus_2 = StringExtensions.operator_plus(_operator_plus_1, "\' in \'");
        String _operator_plus_3 = StringExtensions.operator_plus(_operator_plus_2, path);
        String _operator_plus_4 = StringExtensions.operator_plus(_operator_plus_3, "\'");
        this.logger.logInfo(_operator_plus_4);
        this.fileAccess.setOutputPath(path);
        SubSystemClass _subSystemClass_3 = ssi.getSubSystemClass();
        StringConcatenation _generateHeaderFile = this.generateHeaderFile(root, ssi, _subSystemClass_3);
        this.fileAccess.generateFile(file, _generateHeaderFile);
        SubSystemClass _subSystemClass_4 = ssi.getSubSystemClass();
        String _cSourceFileName = this.stdExt.getCSourceFileName(_subSystemClass_4);
        file = _cSourceFileName;
        String _operator_plus_5 = StringExtensions.operator_plus("generating SubSystemClass implementation: \'", file);
        String _operator_plus_6 = StringExtensions.operator_plus(_operator_plus_5, "\' in \'");
        String _operator_plus_7 = StringExtensions.operator_plus(_operator_plus_6, path);
        String _operator_plus_8 = StringExtensions.operator_plus(_operator_plus_7, "\'");
        this.logger.logInfo(_operator_plus_8);
        this.fileAccess.setOutputPath(path);
        SubSystemClass _subSystemClass_5 = ssi.getSubSystemClass();
        StringConcatenation _generateSourceFile = this.generateSourceFile(root, ssi, _subSystemClass_5);
        this.fileAccess.generateFile(file, _generateSourceFile);
        SubSystemClass _subSystemClass_6 = ssi.getSubSystemClass();
        String _instSourceFileName = this.stdExt.getInstSourceFileName(_subSystemClass_6);
        file = _instSourceFileName;
        String _operator_plus_9 = StringExtensions.operator_plus("generating SubSystemClass instance file: \'", file);
        String _operator_plus_10 = StringExtensions.operator_plus(_operator_plus_9, "\' in \'");
        String _operator_plus_11 = StringExtensions.operator_plus(_operator_plus_10, path);
        String _operator_plus_12 = StringExtensions.operator_plus(_operator_plus_11, "\'");
        this.logger.logInfo(_operator_plus_12);
        this.fileAccess.setOutputPath(path);
        SubSystemClass _subSystemClass_7 = ssi.getSubSystemClass();
        StringConcatenation _generateInstanceFile = this.generateInstanceFile(root, ssi, _subSystemClass_7);
        this.fileAccess.generateFile(file, _generateInstanceFile);
        SubSystemClass _subSystemClass_8 = ssi.getSubSystemClass();
        String _dispSourceFileName = this.stdExt.getDispSourceFileName(_subSystemClass_8);
        file = _dispSourceFileName;
        String _operator_plus_13 = StringExtensions.operator_plus("generating SubSystemClass dispatcher file: \'", file);
        String _operator_plus_14 = StringExtensions.operator_plus(_operator_plus_13, "\' in \'");
        String _operator_plus_15 = StringExtensions.operator_plus(_operator_plus_14, path);
        String _operator_plus_16 = StringExtensions.operator_plus(_operator_plus_15, "\'");
        this.logger.logInfo(_operator_plus_16);
        this.fileAccess.setOutputPath(path);
        SubSystemClass _subSystemClass_9 = ssi.getSubSystemClass();
        StringConcatenation _generateDispatcherFile = this.generateDispatcherFile(root, ssi, _subSystemClass_9);
        this.fileAccess.generateFile(file, _generateDispatcherFile);
      }
    }
  }
  
  public StringConcatenation generateHeaderFile(final Root root, final SubSystemInstance ssi, final SubSystemClass ssc) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* @author generated by eTrice");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Header File of SubSystemClass ");
    String _name = ssc.getName();
    _builder.append(_name, " ");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("* ");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.newLine();
    String _name_1 = ssc.getName();
    StringConcatenation _generateIncludeGuardBegin = this.stdExt.generateIncludeGuardBegin(_name_1);
    _builder.append(_generateIncludeGuardBegin, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    DetailCode _userCode1 = ssc.getUserCode1();
    StringConcatenation _userCode = this.helpers.userCode(_userCode1);
    _builder.append(_userCode, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    _builder.append("/* lifecycle functions");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* init -> start -> run (loop) -> stop -> destroy");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void ");
    String _name_2 = ssc.getName();
    _builder.append(_name_2, "");
    _builder.append("_init(void);\t\t/* lifecycle init  \t */");
    _builder.newLineIfNotEmpty();
    _builder.append("void ");
    String _name_3 = ssc.getName();
    _builder.append(_name_3, "");
    _builder.append("_start(void);\t/* lifecycle start \t */");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("void ");
    String _name_4 = ssc.getName();
    _builder.append(_name_4, "");
    _builder.append("_run(void);\t\t/* lifecycle run \t */");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("void ");
    String _name_5 = ssc.getName();
    _builder.append(_name_5, "");
    _builder.append("_stop(void); \t/* lifecycle stop\t */");
    _builder.newLineIfNotEmpty();
    _builder.append("void ");
    String _name_6 = ssc.getName();
    _builder.append(_name_6, "");
    _builder.append("_destroy(void); \t/* lifecycle destroy */");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    DetailCode _userCode2 = ssc.getUserCode2();
    StringConcatenation _userCode_1 = this.helpers.userCode(_userCode2);
    _builder.append(_userCode_1, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _name_7 = ssc.getName();
    StringConcatenation _generateIncludeGuardEnd = this.stdExt.generateIncludeGuardEnd(_name_7);
    _builder.append(_generateIncludeGuardEnd, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation generateSourceFile(final Root root, final SubSystemInstance ssi, final SubSystemClass ssc) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* @author generated by eTrice");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Source File of SubSystemClass ");
    String _name = ssc.getName();
    _builder.append(_name, " ");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("* ");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"");
    String _cHeaderFileName = this.stdExt.getCHeaderFileName(ssc);
    _builder.append(_cHeaderFileName, "");
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("/* include instances for all classes */");
    _builder.newLine();
    _builder.append("#include \"");
    String _instSourceFileName = this.stdExt.getInstSourceFileName(ssc);
    _builder.append(_instSourceFileName, "");
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.append("#include \"");
    String _dispSourceFileName = this.stdExt.getDispSourceFileName(ssc);
    _builder.append(_dispSourceFileName, "");
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include \"etLogger.h\"");
    _builder.newLine();
    _builder.append("#include \"etMSCLogger.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    DetailCode _userCode3 = ssc.getUserCode3();
    StringConcatenation _userCode = this.helpers.userCode(_userCode3);
    _builder.append(_userCode, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("/* data for SubSysten ");
    String _name_1 = ssc.getName();
    _builder.append(_name_1, "");
    _builder.append(" */");
    _builder.newLineIfNotEmpty();
    _builder.append("typedef struct ");
    String _name_2 = ssc.getName();
    _builder.append(_name_2, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("char *name;");
    _builder.newLine();
    _builder.append("} ");
    String _name_3 = ssc.getName();
    _builder.append(_name_3, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("static ");
    String _name_4 = ssc.getName();
    _builder.append(_name_4, "");
    _builder.append(" ");
    String _name_5 = ssc.getName();
    _builder.append(_name_5, "");
    _builder.append("Inst = {\"");
    String _name_6 = ssc.getName();
    _builder.append(_name_6, "");
    _builder.append("\"};");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("void ");
    String _name_7 = ssc.getName();
    _builder.append(_name_7, "");
    _builder.append("_initActorInstances(void);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("void ");
    String _name_8 = ssc.getName();
    _builder.append(_name_8, "");
    _builder.append("_init(void){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_ENTRY(\"SubSys\", \"init\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("etLogger_logInfoF(\"%s_init\", ");
    String _name_9 = ssc.getName();
    _builder.append(_name_9, "	");
    _builder.append("Inst.name);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* initialization of all message services */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("etMessageService_init(&msgService_Thread1, msgBuffer_Thread1, MESSAGE_POOL_MAX, MESSAGE_BLOCK_SIZE, MsgDispatcher_Thread1_receiveMessage);");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* init all actors */");
    _builder.newLine();
    _builder.append("\t");
    String _name_10 = ssc.getName();
    _builder.append(_name_10, "	");
    _builder.append("_initActorInstances();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_EXIT");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void ");
    String _name_11 = ssc.getName();
    _builder.append(_name_11, "");
    _builder.append("_start(void){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_ENTRY(\"SubSys\", \"start\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("etLogger_logInfoF(\"%s_start\", ");
    String _name_12 = ssc.getName();
    _builder.append(_name_12, "	");
    _builder.append("Inst.name);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_EXIT");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void ");
    String _name_13 = ssc.getName();
    _builder.append(_name_13, "");
    _builder.append("_run(void){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_ENTRY(\"SubSys\", \"run\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int32 i;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("for (i=0; i<100; i++){");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("etLogger_logInfoF(\"%s Scheduler tick %d\", ");
    String _name_14 = ssc.getName();
    _builder.append(_name_14, "		");
    _builder.append("Inst.name, i);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("etMessageService_execute(&msgService_Thread1);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_EXIT");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void ");
    String _name_15 = ssc.getName();
    _builder.append(_name_15, "");
    _builder.append("_stop(void){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_ENTRY(\"SubSys\", \"stop\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("etLogger_logInfoF(\"%s_stop\", ");
    String _name_16 = ssc.getName();
    _builder.append(_name_16, "	");
    _builder.append("Inst.name);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_EXIT");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void ");
    String _name_17 = ssc.getName();
    _builder.append(_name_17, "");
    _builder.append("_destroy(void){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_ENTRY(\"SubSys\", \"destroy\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("etLogger_logInfoF(\"%s_destroy\", ");
    String _name_18 = ssc.getName();
    _builder.append(_name_18, "	");
    _builder.append("Inst.name);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_EXIT");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void ");
    String _name_19 = ssc.getName();
    _builder.append(_name_19, "");
    _builder.append("_initActorInstances(void){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_ENTRY(\"");
    String _name_20 = ssc.getName();
    _builder.append(_name_20, "	");
    _builder.append("\", \"initActorInstances\")");
    _builder.newLineIfNotEmpty();
    {
      EList<ActorInstance> _allContainedInstances = ssi.getAllContainedInstances();
      for(final ActorInstance ai : _allContainedInstances) {
        _builder.append("\t");
        ActorClass _actorClass = ai.getActorClass();
        String _name_21 = _actorClass.getName();
        _builder.append(_name_21, "	");
        _builder.append("_init(&");
        String _path = ai.getPath();
        String _pathName = this.roomExt.getPathName(_path);
        _builder.append(_pathName, "	");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_EXIT");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation generateInstanceFile(final Root root, final SubSystemInstance ssi, final SubSystemClass ssc) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* @author generated by eTrice");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Instance File of SubSystemClass ");
    String _name = ssc.getName();
    _builder.append(_name, " ");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("* - instantiation of all actor instances and port instances");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* - configuration of data and connection of ports");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"etMessageService.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/* instantiation of message services */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/* pool and block size */");
    _builder.newLine();
    _builder.append("#define MESSAGE_POOL_MAX 10");
    _builder.newLine();
    _builder.append("#define MESSAGE_BLOCK_SIZE 32");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/* MessageService for Thread1 */");
    _builder.newLine();
    _builder.append("static uint8 msgBuffer_Thread1[MESSAGE_POOL_MAX*MESSAGE_BLOCK_SIZE];");
    _builder.newLine();
    _builder.append("static etMessageService msgService_Thread1;");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("/* include all used ActorClasses */");
    _builder.newLine();
    {
      EList<ActorClass> _usedActorClasses = root.getUsedActorClasses();
      for(final ActorClass actorClass : _usedActorClasses) {
        _builder.append("#include \"");
        String _name_1 = actorClass.getName();
        _builder.append(_name_1, "");
        _builder.append(".h\"");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("/* include all used ProtcolClasses */");
    _builder.newLine();
    {
      EList<ProtocolClass> _usedProtocolClasses = root.getUsedProtocolClasses();
      for(final ProtocolClass protocolClass : _usedProtocolClasses) {
        _builder.append("#include \"");
        String _name_2 = protocolClass.getName();
        _builder.append(_name_2, "");
        _builder.append(".h\"");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.newLine();
    _builder.append("/* declarations of all ActorClass instances (const and variable structs) */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/* forward declaration of variable actor structs */");
    _builder.newLine();
    {
      EList<ActorInstance> _allContainedInstances = ssi.getAllContainedInstances();
      for(final ActorInstance ai : _allContainedInstances) {
        _builder.append("static ");
        ActorClass _actorClass = ai.getActorClass();
        String _name_3 = _actorClass.getName();
        _builder.append(_name_3, "");
        _builder.append(" ");
        String _path = ai.getPath();
        String _pathName = this.roomExt.getPathName(_path);
        _builder.append(_pathName, "");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<ActorInstance> _allContainedInstances_1 = ssi.getAllContainedInstances();
      for(final ActorInstance ai_1 : _allContainedInstances_1) {
        _builder.newLine();
        _builder.append("/* instance ");
        String _path_1 = ai_1.getPath();
        String _pathName_1 = this.roomExt.getPathName(_path_1);
        _builder.append(_pathName_1, "");
        _builder.append(" */");
        _builder.newLineIfNotEmpty();
        _builder.append("static const ");
        ActorClass _actorClass_1 = ai_1.getActorClass();
        String _name_4 = _actorClass_1.getName();
        _builder.append(_name_4, "");
        _builder.append("_const ");
        String _path_2 = ai_1.getPath();
        String _pathName_2 = this.roomExt.getPathName(_path_2);
        _builder.append(_pathName_2, "");
        _builder.append("_const = {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("/* Ports: {myActor, etReceiveMessage, msgService, peerAddress, localId} */");
        _builder.newLine();
        {
          EList<PortInstance> _ports = ai_1.getPorts();
          for(final PortInstance pi : _ports) {
            _builder.append("\t");
            String _genPortInitializer = this.genPortInitializer(root, ai_1, pi);
            _builder.append(_genPortInitializer, "	");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.newLine();
        _builder.append("};");
        _builder.newLine();
        _builder.append("static ");
        ActorClass _actorClass_2 = ai_1.getActorClass();
        String _name_5 = _actorClass_2.getName();
        _builder.append(_name_5, "");
        _builder.append(" ");
        String _path_3 = ai_1.getPath();
        String _pathName_3 = this.roomExt.getPathName(_path_3);
        _builder.append(_pathName_3, "");
        _builder.append(" = {&");
        String _path_4 = ai_1.getPath();
        String _pathName_4 = this.roomExt.getPathName(_path_4);
        _builder.append(_pathName_4, "");
        _builder.append("_const};");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  private String genPortInitializer(final Root root, final ActorInstance ai, final PortInstance pi) {
    String _xblockexpression = null;
    {
      String _xifexpression = null;
      EList<InterfaceItemInstance> _peers = pi.getPeers();
      boolean _isEmpty = _peers.isEmpty();
      if (_isEmpty) {
        _xifexpression = "NULL";
      } else {
        ActorClass _actorClass = ai.getActorClass();
        String _name = _actorClass.getName();
        String _operator_plus = StringExtensions.operator_plus(_name, "_ReceiveMessage");
        _xifexpression = _operator_plus;
      }
      String recvMsg = _xifexpression;
      int _xifexpression_1 = (int) 0;
      EList<InterfaceItemInstance> _peers_1 = pi.getPeers();
      boolean _isEmpty_1 = _peers_1.isEmpty();
      if (_isEmpty_1) {
        _xifexpression_1 = 0;
      } else {
        EList<InterfaceItemInstance> _peers_2 = pi.getPeers();
        InterfaceItemInstance _get = _peers_2.get(0);
        int _objId = _get.getObjId();
        _xifexpression_1 = _objId;
      }
      int objId = _xifexpression_1;
      String _path = ai.getPath();
      String _pathName = this.roomExt.getPathName(_path);
      String _operator_plus_1 = StringExtensions.operator_plus("{&", _pathName);
      String _operator_plus_2 = StringExtensions.operator_plus(_operator_plus_1, ", ");
      String _operator_plus_3 = StringExtensions.operator_plus(_operator_plus_2, recvMsg);
      String _operator_plus_4 = StringExtensions.operator_plus(_operator_plus_3, ", ");
      String _operator_plus_5 = StringExtensions.operator_plus(_operator_plus_4, "&msgService_Thread1, ");
      String _operator_plus_6 = StringExtensions.operator_plus(_operator_plus_5, ((Integer)objId));
      String _operator_plus_7 = StringExtensions.operator_plus(_operator_plus_6, ", ");
      ExpandedActorClass _expandedActorClass = root.getExpandedActorClass(ai);
      Port _port = pi.getPort();
      int _interfaceItemLocalId = _expandedActorClass.getInterfaceItemLocalId(_port);
      int _operator_plus_8 = IntegerExtensions.operator_plus(((Integer)_interfaceItemLocalId), ((Integer)1));
      String _operator_plus_9 = StringExtensions.operator_plus(_operator_plus_7, ((Integer)_operator_plus_8));
      String _operator_plus_10 = StringExtensions.operator_plus(_operator_plus_9, "} /* Port ");
      String _name_1 = pi.getName();
      String _operator_plus_11 = StringExtensions.operator_plus(_operator_plus_10, _name_1);
      String _operator_plus_12 = StringExtensions.operator_plus(_operator_plus_11, " */");
      _xblockexpression = (_operator_plus_12);
    }
    return _xblockexpression;
  }
  
  public StringConcatenation generateDispatcherFile(final Root root, final SubSystemInstance ssi, final SubSystemClass ssc) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* @author generated by eTrice");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Dispatcher File of SubSystemClass ");
    String _name = ssc.getName();
    _builder.append(_name, " ");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("* - one generated dispatcher for each MessageService (Thread)");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"etMessageReceiver.h\"");
    _builder.newLine();
    _builder.append("#include \"etLogger.h\"");
    _builder.newLine();
    _builder.append("#include \"etMSCLogger.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static void MsgDispatcher_Thread1_receiveMessage(const etMessage* msg){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_ENTRY(\"MsgDispatcher_Thread1\", \"receiveMessage\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("switch(msg->address){");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      EList<ActorInstance> _allContainedInstances = ssi.getAllContainedInstances();
      for(final ActorInstance ai : _allContainedInstances) {
        _builder.append("\t\t");
        _builder.append("/* interface items of ");
        String _path = ai.getPath();
        _builder.append(_path, "		");
        _builder.append(" */");
        _builder.newLineIfNotEmpty();
        {
          EList<InterfaceItemInstance> _orderedIfItemInstances = ai.getOrderedIfItemInstances();
          for(final InterfaceItemInstance pi : _orderedIfItemInstances) {
            _builder.append("\t\t");
            _builder.append("case ");
            int _objId = pi.getObjId();
            _builder.append(_objId, "		");
            _builder.append(":");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("etPort_receive(&");
            String _path_1 = ai.getPath();
            String _pathName = this.roomExt.getPathName(_path_1);
            _builder.append(_pathName, "			");
            _builder.append("_const.");
            String _name_1 = pi.getName();
            _builder.append(_name_1, "			");
            _builder.append(", msg);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("break;");
            _builder.newLine();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("default:");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("etLogger_logErrorF(\"MessageService_Thread1_ReceiveMessage: address %d does not exist \", msg->address);");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("break;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ET_MSC_LOGGER_SYNC_EXIT");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
